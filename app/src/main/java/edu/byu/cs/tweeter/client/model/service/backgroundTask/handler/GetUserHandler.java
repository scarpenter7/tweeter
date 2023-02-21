package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;


import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends TaskHandler {
    public GetUserHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
        HandlerData hd = new HandlerData();
        hd.setUser(user);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to get user: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to get user due to exception: " + exception.getMessage());
    }
}
