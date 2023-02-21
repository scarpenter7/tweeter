package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {
    View view;

    public LoginPresenter(View view) {
        this.view = view;
    }

    public interface View {
        public void displayMessage(String message);
        public void displayErrorMessage(String message);
        public void displayException(String message);
        public void loginSuccessful(User user, AuthToken authToken);
    }

    public void initiateLogin(String username, String password) {
        String validationMessage = validateLogin(username, password);
        if (validationMessage == null) {
            view.displayMessage("Logging in...");
            UserService service = new UserService();
            service.login(username, password, new LoginObserver());
        }
        else {
            view.displayErrorMessage(validationMessage);
        }

    }

    public String validateLogin(String username, String password) {
        if (username.length() > 0 && username.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (username.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        return null;
    }

    public class LoginObserver implements ServiceObserver {
        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayException(message);
        }

        @Override
        public void handleSuccess(HandlerData handlerData) {
            User user = handlerData.getUser();
            AuthToken authToken = handlerData.getAuthToken();
            view.loginSuccessful(user, authToken);
        }
    }
}
