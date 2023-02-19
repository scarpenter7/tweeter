package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends TaskHandler {

    public GetFollowersHandler(ServiceObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        List<User> followers = (List<User>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        HandlerData hd = new HandlerData();
        hd.setPeople(followers);
        hd.setHasMorePages(hasMorePages);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(Message msg) {
        String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
        observer.handleError("Failed to get followers: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
        observer.handleException("Failed to get followers because of exception: " + ex.getMessage());
    }
}
