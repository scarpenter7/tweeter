package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends TaskHandler {

    public GetFeedHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<Status> statuses = (List<Status>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
        HandlerData handlerData = new HandlerData();
        handlerData.setStatuses(statuses);
        handlerData.setHasMorePages(hasMorePages);

        observer.handleSuccess(handlerData);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to get feed: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to get feed due to exception: " + exception.getMessage());
    }
}