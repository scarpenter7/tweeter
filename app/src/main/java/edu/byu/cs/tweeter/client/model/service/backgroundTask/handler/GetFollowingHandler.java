package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;
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
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to get following: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception ex = getException(msg);
        observer.handleException("Failed to get following because of exception: " + ex.getMessage());
    }
}
