package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter
        implements FollowersService.Observer, FollowService.Observer, UserService.LoginObserver, StoryService.Observer {

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
        this.storyService = new StoryService();
    }

    @Override
    public void displayFollowError(String message) {
        view.displayFollowErrorMessage(message);
    }

    @Override
    public void displayFollowException(Exception exception, String message) {
        view.displayFollowException(exception, message);
    }

    @Override
    public void handleError(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception, String message) {
        view.displayException(exception, message);
    }

    @Override
    public void addStories(List<Status> statuses, boolean hasMorePages) {
        // do not use
    }

    @Override
    public void postStatus() {
        view.postStatus();
    }


    @Override
    public void addFollowees(List<User> followees, boolean hasMorePages) {
        // do nothing
    }

    @Override
    public void follow() {
        view.follow();
    }

    @Override
    public void unfollow() {
        view.unfollow();
    }

    @Override
    public void getFolloweesCount(int count) {
        view.getFolloweesCount(count);
    }


    @Override
    public void addFollowers(List<User> followers, boolean hasMorePages) {
        // don't use
    }

    @Override
    public void isFollower() {
        view.isFollower();
    }

    @Override
    public void isNotFollower() {
        view.isNotFollower();
    }

    @Override
    public void getFollowersCount(int count) {
        view.getFollowersCount(count);
    }

    @Override
    public void handleLoginSuccess(User user, AuthToken authToken) {
        // do not use
    }

    @Override
    public void handleLoginFailure(String message) {
        // do not use
    }

    @Override
    public void handleLogoutSuccess() {
        view.logout();
    }

    @Override
    public void handleLogoutFailure(String message) {
        handleError(message);
    }

    public interface View {
        void setLoadingFooter(boolean value);
        void displayErrorMessage(String message);
        void displayException(Exception exception, String message);
        void displayFollowErrorMessage(String message);
        void displayFollowException(Exception exception, String message);
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
        followersService.isFollower(selectedUser, this);
    }

    public void follow(User user) {
        followService.follow(user, this);
    }
    public void unfollow(User selectedUser) {
        followService.unfollow(selectedUser, this);
    }

    public void logout() {
        userService.logout(this);
        //Clear user data (cached data).
        Cache.getInstance().clearCache();
    }

    public void postStatus(Status newStatus) {
        storyService.postStatus(newStatus, this);
    }

    public void getFollowersCount(User selectedUser) {
        followersService.getFollowersCount(selectedUser, this);
    }

    public void getFolloweesCount(User selectedUser) {
        followService.getFolloweesCount(selectedUser, this);
    }

}
