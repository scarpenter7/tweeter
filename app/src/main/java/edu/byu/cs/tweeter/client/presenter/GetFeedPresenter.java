package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedPresenter implements UserService.GetUserObserver {
    private static final int PAGE_SIZE = 10;
    private GetFeedPresenter.View view;
    private FeedService feedService;
    private UserService userService;
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetFeedPresenter(View view) {
        this.view = view;
        this.feedService = new FeedService();
        this.userService = new UserService();
    }

    public boolean isloading() {
        return isLoading;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void getUser(String username) {
        userService.getUser(username, this);
    }

    @Override
    public void handleSuccess(User user) {
        view.getUserSuccessful(user);
    }

    @Override
    public void handleFailure(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.displayInfoMessage(exception.getMessage());
    }

    public interface View {
        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreItems(List<Status> statuses);

        void getUserSuccessful(User user);

        void displayErrorMessage(String message);

        void displayInfoMessage(String message);
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            feedService.loadMoreItems(user, PAGE_SIZE, lastStatus, new GetFeedPresenter.GetFeedObserver());
        }
    }

    private void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public class GetFeedObserver implements ServiceObserver {

        @Override
        public void handleError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void handleException(String messsage) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(messsage);
        }

        @Override
        public void handleSuccess(HandlerData handlerData) {
            List<Status> statuses = handlerData.getStatuses();
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            setHasMorePages(handlerData.hasMorePages());
            view.addMoreItems(statuses);
        }
    }
}
