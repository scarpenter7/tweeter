package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends Handler {
    FollowersService.Observer observer;
    public IsFollowerHandler(FollowersService.Observer observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
        if (success) {
            boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

            // If logged in user if a follower of the selected user, display the follow button as "following"
            if (isFollower) {
                observer.isFollower();
            } else {
                observer.isNotFollower();
            }
        } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
            observer.handleError("Failed to determine following relationship: " + message);
        } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
            observer.handleException(ex, "Failed to determine following relationship because of exception: ");
        }
    }
}
