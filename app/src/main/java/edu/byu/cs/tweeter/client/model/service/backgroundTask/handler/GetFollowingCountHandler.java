package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;

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
    protected void handleError(String message) {
        observer.handleError("Failed to get following count: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to get following count due to exception: " + exception.getMessage());
    }
}
