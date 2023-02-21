package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

public interface PagedView<T> extends UserView {
    void addMoreItems(List<T> items);
}
