package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public class PostStatusHandler extends TaskHandler {
    public PostStatusHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.handleSuccess(null);
    }

    @Override
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to post status: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception ex = getException(msg);
        observer.handleException("Failed to post status due to exception: " + ex.getMessage());
    }
}
