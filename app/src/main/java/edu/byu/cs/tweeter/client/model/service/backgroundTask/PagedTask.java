package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T> extends AuthenticatedTask {
    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    /**
     * Maximum number of followed users to return (i.e., page size).
     */
    private int limit;
    /**
     * The last person being followed returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    private T lastItem;

    List<T> items;
    boolean hasMorePages;

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     *
     * @param messageHandler
     * @param authToken
     */
    public PagedTask(Handler messageHandler, AuthToken authToken, int limit, T lastItem) {
        super(messageHandler, authToken);
        this.limit = limit;
        this.lastItem = lastItem;
    }

    @Override
    protected void runTask() {
        Pair<List<T>, Boolean> pageOfItems = getItems(); // getFollowees();

        this.items = pageOfItems.getFirst();
        this.hasMorePages = pageOfItems.getSecond();
    }

    public int getLimit() {
        return limit;
    }

    public T getLastItem() {
        return lastItem;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }
    protected abstract Pair<List<T>, Boolean> getItems();
}
