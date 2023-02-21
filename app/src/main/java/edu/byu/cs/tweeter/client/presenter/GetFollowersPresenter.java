package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter extends GetUserObserver<User> {

    private static final int PAGE_SIZE = 10;
    private FollowersService followersService;
    
    private UserService userService;
    private User lastFollower;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetFollowersPresenter(PagedView<User> view) {
        super(view);
        this.followersService = new FollowersService();
        this.userService = new UserService();
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

    public void getUser(String username) {
        userService.getUser(username, this);
    }

    private class GetFollowersObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            List<User> followers = handlerData.getPeople();
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            setHasMorePages(handlerData.hasMorePages());
            view.addMoreItems(followers);
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
