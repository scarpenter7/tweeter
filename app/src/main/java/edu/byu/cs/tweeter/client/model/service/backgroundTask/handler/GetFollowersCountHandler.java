package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;


import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public class GetFollowersCountHandler extends TaskHandler {
    public GetFollowersCountHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
        HandlerData hd = new HandlerData();
        hd.setCount(count);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to get followers count: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception exception = getException(msg);
        observer.handleException("Failed to get followers count due to exception: " + exception.getMessage());
    }
}
