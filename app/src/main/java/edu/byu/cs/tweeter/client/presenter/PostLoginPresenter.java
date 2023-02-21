package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public abstract class PostLoginPresenter extends Presenter {
    private UserService userService;

    public PostLoginPresenter(PresenterView view) {
        super(view);
        this.userService = new UserService();
    }
}
