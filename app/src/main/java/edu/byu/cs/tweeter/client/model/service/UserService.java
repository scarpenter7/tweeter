package edu.byu.cs.tweeter.client.model.service;// edu.byu.cs.tweeter.client.model.service.UserService.java

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.RegisterHandler;
import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.client.view.login.LoginFragment;
import edu.byu.cs.tweeter.client.view.login.RegisterFragment;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {



    public interface LoginObserver {
        void handleSuccess(User user, AuthToken authToken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface RegisterObserver {
        void handleSuccess(User user, AuthToken authToken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface GetUserObserver {
        void handleSuccess(User user);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void login(String username, String password, LoginObserver observer) {
        LoginTask loginTask = new LoginTask(username, password, new LoginHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public void register(String firstName, String lastName, String username, String password,
                         Bitmap image, RegisterObserver observer) {
        // Convert image to byte array.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

        // Send register request.
        RegisterTask registerTask = new RegisterTask(firstName, lastName, username, password,
                imageBytesBase64, new RegisterHandler(observer));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);

    }

    public void getUser(String username) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                username, new GetUserHandler());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }
}