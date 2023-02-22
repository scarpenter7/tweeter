package edu.byu.cs.tweeter.client.model.service;// edu.byu.cs.tweeter.client.model.service.UserService.java

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.RegisterHandler;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends Service {

    public void login(String username, String password, ServiceObserver observer) {
        LoginTask loginTask = new LoginTask(username, password, new LoginHandler(observer));
        executeTask(loginTask);
    }

    public void register(String firstName, String lastName, String username, String password,
                         Bitmap image, ServiceObserver observer) {
        // Convert image to byte array.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

        // Send register request.
        RegisterTask registerTask = new RegisterTask(firstName, lastName, username, password,
                imageBytesBase64, new RegisterHandler(observer));

        executeTask(registerTask);
    }

    public void logout(MainActivityPresenter.LogoutObserver observer) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        executeTask(logoutTask);
    }

    public void getUser(String username, GetUserObserver observer) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                username, new GetUserHandler(observer));
        executeTask(getUserTask);
    }
}