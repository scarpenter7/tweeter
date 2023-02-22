package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface PagedView<T> extends PresenterView {
    void getUserSuccessful(User user);
    void setLoadingFooter(boolean value);
    void addMoreItems(List<T> items);
}
