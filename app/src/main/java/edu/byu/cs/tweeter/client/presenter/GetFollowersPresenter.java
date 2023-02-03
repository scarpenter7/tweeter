package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.main.followers.FollowersFragment;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter {

    private static final int PAGE_SIZE = 10;

    private GetFollowingPresenter.View view;

    private FollowService followService;
    private User lastFollowee;
    private boolean hasMorePages;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);

        void addMoreItems(List<User> followees);
    }
    public GetFollowersPresenter(View view) {
    }

    public void loadMoreItems(User user) {
    }
}
