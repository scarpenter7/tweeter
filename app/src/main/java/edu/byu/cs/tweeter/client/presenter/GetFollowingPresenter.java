package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingPresenter implements UserService.GetUserObserver {
    private static final int PAGE_SIZE = 10;
    private View view;
    private FollowService followService;

    private UserService userService;
    private User lastFollowee;
    private boolean hasMorePages;
    private boolean isLoading = false;

    @Override
    public void handleSuccess(User user) {
        view.getUserSuccessful(user);
    }

    @Override
    public void handleFailure(String message) {
        view.displayError(message);
    }

    @Override
    public void handleException(Exception exception) {
        view.displayInfoMessage(exception.getMessage());
    }


    public interface View {
        void setLoadingFooter(boolean value);
        void displayException(String message);

        void addMoreItems(List<User> followees);

        void getUserSuccessful(User user);

        void displayError(String message);

        void displayInfoMessage(String message);
    }

    public GetFollowingPresenter(View view) {
        this.view = view;
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

    private class GetFollowingObserver implements FollowService.Observer {

        private boolean hasMorePages;

        @Override
        public void displayFollowError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayError(message);
        }

        @Override
        public void displayFollowException(Exception exception, String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayException("Failed to get following because of exception: " + exception.getMessage());
        }

        @Override
        public void addFollowees(List<User> followees, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addMoreItems(followees);
        }

        @Override
        public void follow() {
            // don't use
        }

        @Override
        public void unfollow() {
            // don't use
        }
    }
}
