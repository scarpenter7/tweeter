package edu.byu.cs.tweeter.client.presenter;

public abstract class Presenter {
    PresenterView view;

    public Presenter(PresenterView view) {
        this.view = view;
    }
}
