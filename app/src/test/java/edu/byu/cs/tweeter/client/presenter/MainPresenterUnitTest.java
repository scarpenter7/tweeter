package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterUnitTest {
    private MainActivityPresenter.View mockView;
    private StoryService mockStoryService;

    private MainActivityPresenter mainPresenterSpy;

    @BeforeEach
    public void setup() {
        // create mock objects
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockStoryService = Mockito.mock(StoryService.class);

        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView));

        // Mockito.doReturn(mockStoryService).when(mainPresenterSpy).getStoryService(); this works even when the function is void
        Mockito.when(mainPresenterSpy.getStoryService()).thenReturn(mockStoryService); // does not work when function is void but you get type checking here
    }

    @Test
    public void testPostStatus_postSuccessful() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Status status = invocation.getArgument(0, Status.class);

                List<String> containedUrls = FeedService.parseURLs(status.post);
                List<String> mentions = FeedService.parseMentions(status.post);
                Assertions.assertEquals(containedUrls, List.of("http://byu.edu", "http://google.com"));
                Assertions.assertEquals(mentions, List.of("@homie1", "@homie2"));

                MainActivityPresenter.PostStatusObserver observer = invocation.getArgument(1, MainActivityPresenter.PostStatusObserver.class);

                Assertions.assertNotNull(observer);

                observer.handleSuccess(null);
                return null;
            }
        };


        String post = "Hey @homie1 check out this post http://byu.edu @homie2 also at http://google.com";
        User dummy = new User("firstName", "lastName", "dummy", "imageURL");
        Status newStatus = new Status(post, dummy, System.currentTimeMillis(),
                FeedService.parseURLs(post), FeedService.parseMentions(post));


        Mockito.doAnswer(answer).when(mockStoryService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus(newStatus);
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockView).postStatus();
    }

    @Test
    public void testPostStatus_postError() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Status status = invocation.getArgument(0, Status.class);
                MainActivityPresenter.PostStatusObserver observer = invocation.getArgument(1, MainActivityPresenter.PostStatusObserver.class);
                observer.handleError("the error message");
                return null;
            }
        };


        String post = "check out this post";
        User dummy = new User("firstName", "lastName", "dummy", "imageURL");
        Status newStatus = new Status(post, dummy, System.currentTimeMillis(),
                FeedService.parseURLs(post), FeedService.parseMentions(post));


        Mockito.doAnswer(answer).when(mockStoryService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus(newStatus);
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockView, Mockito.times(0)).postStatus();
        Mockito.verify(mockView).displayErrorMessage("Failed to post status: the error message");
    }

    @Test
    public void testPostStatus_postException() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Status status = invocation.getArgument(0, Status.class);
                MainActivityPresenter.PostStatusObserver observer = invocation.getArgument(1, MainActivityPresenter.PostStatusObserver.class);
                observer.handleException("the exception message");
                return null;
            }
        };


        String post = "check out this post";
        User dummy = new User("firstName", "lastName", "dummy", "imageURL");
        Status newStatus = new Status(post, dummy, System.currentTimeMillis(),
                FeedService.parseURLs(post), FeedService.parseMentions(post));


        Mockito.doAnswer(answer).when(mockStoryService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus(newStatus);
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockView, Mockito.times(0)).postStatus();
        Mockito.verify(mockView).displayException("Failed to post status because of exception: the exception message");
    }
}
