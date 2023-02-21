package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {
    View view;
    public RegisterPresenter(RegisterPresenter.View view) {
        this.view = view;
    }

    public void initiateRegister(String firstName, String lastName, String username, String password,
                                 Bitmap image) {
        String validationMessage = validateRegistration(firstName, lastName, username, password, image);
        if (validationMessage == null) {
            view.displayInfoMessage("Registering/Logging in...");
            UserService service = new UserService();
            service.register(firstName, lastName, username, password, image, new RegisterObserver());
        }
        else {
            view.displayErrorMessage(validationMessage);
        }
    }

    public interface View {
        public void displayInfoMessage(String message);
        public void displayErrorMessage(String message);
        public void registerSuccessful(User user, AuthToken authToken);
    }
    public String validateRegistration(String firstName, String lastName, String username, String password, Bitmap image) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (username.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (username.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (username.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        if (image == null) { //may need to fix this, it's possible there is a seg fault when the Bitmap is generated first
            return "Profile image must be uploaded.";
        }

        return null;
    }

    public class RegisterObserver implements ServiceObserver {
        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayInfoMessage(message);
        }

        @Override
        public void handleSuccess(HandlerData handlerData) {
            User user = handlerData.getUser();
            AuthToken authToken = handlerData.getAuthToken();
            view.registerSuccessful(user, authToken);
        }
    }
}
