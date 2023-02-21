package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;

public class FollowHandler extends TaskHandler {

    public FollowHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.handleSuccess(null);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to follow: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to follow due to exception: " + exception.getMessage());
    }
}
