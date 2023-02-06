package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter implements FollowersService.Observer, FollowService.Observer, UserService.LoginObserver {

    private MainActivityPresenter.View view;
    private FollowersService followersService;
    private FollowService followService;
    private UserService userService;

    public MainActivityPresenter(View view) {
        this.view = view;
        this.followersService = new FollowersService();
        this.followService = new FollowService();
        this.userService = new UserService();
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
    public void displayError(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(Exception exception, String message) {
        view.displayException(exception, message);
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
        displayError(message);
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
}
