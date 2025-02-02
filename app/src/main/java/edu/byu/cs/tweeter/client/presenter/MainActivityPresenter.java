package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    private MainActivityPresenter.View view;
    private FollowersService followersService;
    private FollowService followService;
    private UserService userService;
    private StoryService storyService;

    public MainActivityPresenter(View view) {
        this.view = view;
        this.followersService = new FollowersService();
        this.followService = new FollowService();
        this.userService = new UserService();
    }

    public StoryService getStoryService() {
        if (storyService ==null) {
            storyService = new StoryService();
        }

        return storyService;
    }

    public interface View {
        void setLoadingFooter(boolean value);
        void displayErrorMessage(String message);
        void displayInfoMessage(String message);
        void displayException(String message);
        void displayFollowErrorMessage(String message);
        void displayFollowException(String message);
        void isFollower();
        void isNotFollower();
        void follow();
        void unfollow();
        void logout();
        void postStatus();
        void getFollowersCount(int count);
        void getFolloweesCount(int count);
    }

    public void isFollower(User selectedUser) {
        followersService.isFollower(selectedUser, new IsFollowerObserver());
    }

    public void follow(User user) {
        followService.follow(user, new FollowObserver());
    }
    public void unfollow(User selectedUser) {
        followService.unfollow(selectedUser, new UnfollowObserver());
    }

    public void logout() {
        userService.logout(new LogoutObserver());
        //Clear user data (cached data).
        Cache.getInstance().clearCache();
    }

    public void postStatus(Status newStatus) {
        view.displayInfoMessage("Posting Status...");
        getStoryService().postStatus(newStatus, new PostStatusObserver());
    }

    public void getFollowersCount(User selectedUser) {
        followersService.getFollowersCount(selectedUser, new GetFollowersCountObserver());
    }

    public void getFolloweesCount(User selectedUser) {
        followService.getFolloweesCount(selectedUser, new GetFolloweesCountObserver());
    }

    public class PostStatusObserver implements ServiceObserver {
        @Override
        public void handleSuccess(HandlerData handlerData) {
            view.postStatus();
        }

        @Override
        public void handleError(String message) {
            view.displayErrorMessage("Failed to post status: " + message);
        }

        @Override
        public void handleException(String message) {
            view.displayException("Failed to post status because of exception: " + message);
        }
    }

    public class FollowObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            view.follow();
        }

        @Override
        public void handleError(String message) {
            view.displayFollowErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayFollowException(message);
        }
    }

    public class UnfollowObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            view.unfollow();
        }

        @Override
        public void handleError(String message) {
            view.displayFollowErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayFollowException(message);
        }
    } // TODO remove code dup here with Follow Observer

    public class GetFolloweesCountObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            view.getFolloweesCount(handlerData.getCount());
        }

        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayException(message);
        }
    }

    public class GetFollowersCountObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            view.getFollowersCount(handlerData.getCount());
        }

        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayException(message);
        }
    }

    public class IsFollowerObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            boolean isFollower = handlerData.isFollower();
            if (isFollower) {
                view.isFollower();
            } else {
                view.isNotFollower();
            }
        }

        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayException(message);
        }
    }

    public class LogoutObserver implements ServiceObserver {

        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayException(message);
        }

        @Override
        public void handleSuccess(HandlerData handlerData) {
            view.logout();
        }
    }
}
