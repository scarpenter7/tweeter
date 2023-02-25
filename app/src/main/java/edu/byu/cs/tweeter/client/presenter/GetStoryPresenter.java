package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStoryPresenter extends GetUserObserver<Status> {
    private StoryService storyService;

    public GetStoryPresenter(PagedView<Status> view) {
        super(view);
        this.storyService = new StoryService();
    }

    @Override
    protected void loadMore(User user) {
        storyService.loadMoreItems(user, PAGE_SIZE, lastItem, new GetStoryObserver());
    }

    public class GetStoryObserver implements ServiceObserver {
        @Override
        public void handleSuccess(HandlerData handlerData) {
            List<Status> statuses = handlerData.getStatuses();
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastItem = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            setHasMorePages(handlerData.hasMorePages());
            view.addMoreItems(statuses);
        }

        @Override
        public void handleError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void handleException(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }
    }
}
