package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class HandlerData {
    private List<Status> statuses;
    private boolean hasMorePages;

    private boolean isFollower;

    public boolean isFollower() {
        return isFollower;
    }

    public void setIsFollower(boolean follower) {
        isFollower = follower;
    }

    private User user;
    private List<User> people;
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public User getUser() {
        return user;
    }

    public List<User> getPeople() { return people; }

    public void setPeople(List<User> people) {
        this.people = people;
    }

}
