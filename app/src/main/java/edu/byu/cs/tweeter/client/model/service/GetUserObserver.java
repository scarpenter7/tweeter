package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.client.presenter.GetFollowingPresenter;
import edu.byu.cs.tweeter.client.presenter.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class GetUserObserver<T> implements ServiceObserver {
    protected static final int PAGE_SIZE = 10;
    protected PagedView<T> view;
    protected UserService userService;
    protected Service service;
    protected T lastItem;
    protected boolean hasMorePages;
    protected boolean isLoading = false;
    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void getUser(String username) {
        userService.getUser(username, this);
    }

    public GetUserObserver(PagedView<T> view) {
        this.view = view;
        this.userService = new UserService();
    }

    @Override
    public void handleSuccess(HandlerData handlerData) {
        view.getUserSuccessful(handlerData.getUser());
    }

    @Override
    public void handleError(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(String message) {
        view.setErrorView(message);
    }

    public boolean isloading() {
        return isLoading;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            loadMore(user);
        }
    }

    protected abstract void loadMore(User user);
}
