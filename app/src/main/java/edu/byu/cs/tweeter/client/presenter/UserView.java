package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserView extends PresenterView {
    void getUserSuccessful(User user);
    void setLoadingFooter(boolean value);
}
