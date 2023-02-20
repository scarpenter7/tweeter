package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public class GetFollowingCountHandler extends TaskHandler {
    public GetFollowingCountHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
        HandlerData hd = new HandlerData();
        hd.setCount(count);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to get following count: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception exception = getException(msg);
        observer.handleException("Failed to get following count due to exception: " + exception.getMessage());
    }
}
