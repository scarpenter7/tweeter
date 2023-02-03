package edu.byu.cs.tweeter.client.model.service;

import java.util.List;

import edu.byu.cs.tweeter.client.presenter.GetFeedPresenter;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService {

    public interface Observer {

        void displayError(String message);

        void displayException(Exception exception);

        void addStatuses(List<Status> statuses, boolean hasMorePages);
    }

    public void loadMoreItems(User user, int pageSize, Status lastStatus, Observer observer) {

    }
}
