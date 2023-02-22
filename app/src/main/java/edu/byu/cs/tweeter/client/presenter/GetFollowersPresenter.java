package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HandlerData;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter extends GetUserObserver<User> {
    private FollowersService followersService;

    public GetFollowersPresenter(PagedView<User> view) {
        super(view);
        this.followersService = new FollowersService();
    }

    @Override
    protected void loadMore(User user) {
        followersService.loadMoreItems(user, PAGE_SIZE, lastItem, new GetFollowersObserver());
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private class GetFollowersObserver implements ServiceObserver {

        @Override
        public void handleSuccess(HandlerData handlerData) {
            List<User> followers = handlerData.getPeople();
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastItem = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            setHasMorePages(handlerData.hasMorePages());
            view.addMoreItems(followers);
        }

        @Override
        public void handleError(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }

        @Override
        public void handleException(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayMessage(message);
        }
    }
}
