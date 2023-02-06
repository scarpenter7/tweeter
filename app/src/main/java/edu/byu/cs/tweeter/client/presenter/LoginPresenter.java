package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter implements UserService.LoginObserver {
    View view;

    public LoginPresenter(View view) {
        this.view = view;
    }


    @Override
    public void handleLoginSuccess(User user, AuthToken authToken) {
        view.loginSuccessful(user, authToken);
    }

    @Override
    public void handleLoginFailure(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleLogoutSuccess() {
        // don't use
    }

    @Override
    public void handleLogoutFailure(String message) {
        // don't use
    }

    @Override
    public void handleException(Exception exception, String message) {
        view.displayException(exception, message);

    }

    public interface View {
        public void displayMessage(String message);
        public void displayErrorMessage(String message);
        public void displayException(Exception exception, String message);
        public void loginSuccessful(User user, AuthToken authToken);
    }

    public void initiateLogin(String username, String password) {
        String validationMessage = validateLogin(username, password);
        if (validationMessage == null) {
            view.displayMessage("Logging in...");
            UserService service = new UserService();
            service.login(username, password, this);
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
}
