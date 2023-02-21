package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends TaskHandler {

    public GetFollowingHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<User> followees = (List<User>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);// TODO about this warning message
        boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        HandlerData hd = new HandlerData();
        hd.setPeople(followees);
        hd.setHasMorePages(hasMorePages);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to get following: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to get following because of exception: " + exception.getMessage());
    }
}
