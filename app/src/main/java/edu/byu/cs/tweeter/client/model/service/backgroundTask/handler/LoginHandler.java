package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;


import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginHandler extends TaskHandler {

    public LoginHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

        // Cache user session information
        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        HandlerData hd = new HandlerData();
        hd.setUser(loggedInUser);
        hd.setAuthToken(authToken);
        observer.handleSuccess(hd);
        //observer.handleLoginSuccess(loggedInUser, authToken);

    }

    @Override
    protected void handleError(String message) {
        observer.handleError("Failed to login: " + message);
    }

    @Override
    protected void handleException(Exception exception) {
        observer.handleError("Failed to login due to exception: " + exception.getMessage());
    }
}