package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends TaskHandler {

    public GetFollowersHandler(ServiceObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        List<User> followers = (List<User>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);
        HandlerData hd = new HandlerData();
        hd.setPeople(followers);
        hd.setHasMorePages(hasMorePages);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to get followers: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to get followers because of exception: " + exception.getMessage());
    }
}
