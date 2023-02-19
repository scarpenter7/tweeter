package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;
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
    protected void handleError(Message msg) {
        String message = getErrorInfoMessage(msg);
        observer.handleError("Failed to get story: " + message);
    }

    @Override
    protected void handleException(Message msg) {
        Exception ex = getException(msg);
        observer.handleException("Failed to get story due to exception: " + ex.getMessage());
    }
}