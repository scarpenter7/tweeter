package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter implements FollowersService.Observer {

    private MainActivityPresenter.View view;
    private FollowersService followersService;

    @Override
    public void displayError(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void displayException(Exception exception) {
        view.displayException(exception);
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

    public interface View {
        void setLoadingFooter(boolean value);
        void displayErrorMessage(String message);
        void displayException(Exception exception);
        void isFollower();
        void isNotFollower();
    }

    public MainActivityPresenter(View view) {
        this.view = view;
        this.followersService = new FollowersService();
    }
    public void isFollower(User selectedUser) {
        followersService.isFollower(selectedUser, this);
    }
}
