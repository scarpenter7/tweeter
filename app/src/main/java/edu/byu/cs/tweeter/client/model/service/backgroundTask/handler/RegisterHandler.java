package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends TaskHandler {

    public RegisterHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(registeredUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        HandlerData hd = new HandlerData();
        hd.setUser(registeredUser);
        hd.setAuthToken(authToken);
        observer.handleSuccess(hd);
    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to register: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleException("Failed to register: " + exception.getMessage());
    }
}

