package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends TaskHandler {

    public GetStoryHandler(ServiceObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        List<Status> statuses = (List<Status>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
        HandlerData hd = new HandlerData();
        hd.setHasMorePages(hasMorePages);
        hd.setStatuses(statuses);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to get story: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to get story due to exception: " + exception.getMessage());
    }
}