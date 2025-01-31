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
    private FeedService feedService;

    public GetFeedPresenter(PagedView<Status> view) {
        super(view);
        this.feedService = new FeedService();
    }

    @Override
    protected void loadMore(User user) {
        feedService.loadMoreItems(user, PAGE_SIZE, lastItem, new GetFeedObserver());
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
            lastItem = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            setHasMorePages(handlerData.hasMorePages());
            view.addMoreItems(statuses);
        }
    }
}
