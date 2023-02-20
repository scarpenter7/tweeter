package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public class UnfollowHandler extends TaskHandler {

    public UnfollowHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.handleSuccess(null);
    }

    @Override
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to unfollow: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception exception = getException(msg);
        observer.handleException("Failed to unfollow due to exception: " + exception.getMessage());
    }
}
