package edu.byu.cs.tweeter.client.presenter;

public interface PresenterView {
    void displayMessage(String message);
    void displayErrorMessage(String message);
    void setErrorView(String message);
}
