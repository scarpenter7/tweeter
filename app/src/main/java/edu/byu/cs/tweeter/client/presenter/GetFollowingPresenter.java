package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingPresenter extends GetUserObserver<User> {
    private static final int PAGE_SIZE = 10;
    private FollowService followService;

    private UserService userService;
    private User lastFollowee;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetFollowingPresenter(PagedView<User> view) {
        super(view);
        this.followService = new FollowService();
        this.userService = new UserService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            followService.loadMoreItems(user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
        }
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void getUser(String username) {
        userService.getUser(username, this);

    }

    private class GetFollowingObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            List<User> followees = handlerData.getPeople();
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            setHasMorePages(handlerData.hasMorePages());
            view.addMoreItems(followees);
        }

        @Override
        public void handleError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.setErrorView(message);
        }
    }
}
