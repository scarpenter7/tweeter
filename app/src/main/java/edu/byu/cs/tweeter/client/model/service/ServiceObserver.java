package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;

public interface ServiceObserver {
    void handleSuccess(HandlerData handlerData);
    void handleError(String message);
    void handleException(String message);

}
