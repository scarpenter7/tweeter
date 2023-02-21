package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedPresenter extends GetUserObserver<Status> {
    private static final int PAGE_SIZE = 10;
    private FeedService feedService;
    private UserService userService;
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetFeedPresenter(PagedView<Status> view) {
        super(view);
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
