package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public abstract class TaskHandler extends Handler {

    ServiceObserver observer;

    public TaskHandler(ServiceObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(BackgroundTask.SUCCESS_KEY);
        if (success) {
            handleSuccess(msg);
        } else if (msg.getData().containsKey(BackgroundTask.MESSAGE_KEY)) {
            String message = getErrorInfoMessage(msg);
            handleError(message);
        } else if (msg.getData().containsKey(BackgroundTask.EXCEPTION_KEY)) {
            Exception exception = getException(msg);
            handleException(exception);
        }
    }

    protected abstract void handleSuccess(Message msg);
    protected abstract void handleError(String message);
    protected abstract void handleException(Exception exception);

    private String getErrorInfoMessage(Message msg) {
        return msg.getData().getString(BackgroundTask.MESSAGE_KEY);
    }

    private Exception getException(Message msg) {
        return (Exception) msg.getData().getSerializable(BackgroundTask.EXCEPTION_KEY);
    }

}
