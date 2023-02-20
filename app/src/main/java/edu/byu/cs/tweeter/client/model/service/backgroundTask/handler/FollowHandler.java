package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public class FollowHandler extends TaskHandler {

    public FollowHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.handleSuccess(null);
    }

    @Override
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to follow: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception exception = getException(msg);
        observer.handleException("Failed to follow due to exception: " + exception.getMessage());
    }
}
