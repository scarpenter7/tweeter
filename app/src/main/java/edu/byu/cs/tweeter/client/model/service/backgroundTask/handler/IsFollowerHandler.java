package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public class IsFollowerHandler extends TaskHandler {
    public IsFollowerHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        HandlerData hd = new HandlerData();
        hd.setIsFollower(isFollower);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to determine following relationship: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to determine following relationship due to exception: " + exception.getMessage());
    }
}
