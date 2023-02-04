package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStoryPresenter {
    private static final int PAGE_SIZE = 10;
    private View view;
    private StoryService storyService;
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);

        void addMoreItems(List<Status> statuses);
    }
    public GetStoryPresenter(View view) {
        this.view = view;
        this.storyService = new StoryService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            storyService.loadMoreItems(user, PAGE_SIZE, lastStatus, new GetStoryPresenter.GetStoriesObserver());
        }
    }

    private void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public class GetStoriesObserver implements StoryService.Observer {
        @Override
        public void displayError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage("Failed to get story because of exception: " + exception.getMessage());
        }

        @Override
        public void addStories(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addMoreItems(statuses);
        }
    }


}