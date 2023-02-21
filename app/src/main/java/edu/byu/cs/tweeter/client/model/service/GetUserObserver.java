package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.client.presenter.UserView;

public class GetUserObserver implements ServiceObserver {

    protected UserView view;

    public GetUserObserver(UserView view) {
        this.view = view;
    }

    @Override
    public void handleSuccess(HandlerData handlerData) {
        view.getUserSuccessful(handlerData.getUser());
    }

    @Override
    public void handleError(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(String message) {
        view.setErrorView(message);
    }
}
