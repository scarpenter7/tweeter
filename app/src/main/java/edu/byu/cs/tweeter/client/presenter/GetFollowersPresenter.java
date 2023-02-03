package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.view.main.followers.FollowersFragment;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter {

    private static final int PAGE_SIZE = 10;
    private GetFollowersPresenter.View view;
    private FollowersService followersService;
    private User lastFollower;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);

        void addMoreItems(List<User> followees);
    }
    public GetFollowersPresenter(View view) {
        this.view = view;
        this.followersService = new FollowersService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            followersService.loadMoreItems(user, PAGE_SIZE, lastFollower, new GetFollowersPresenter.GetFollowersObserver());
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

    private class GetFollowersObserver implements FollowersService.Observer {

        private boolean hasMorePages;

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
            view.displayMessage("Failed to get followers because of exception: " + exception.getMessage());
        }

        @Override
        public void addFollowers(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addMoreItems(followers);
        }
    }
}
