package com.nilesh.jirahook.controller;

import com.google.gson.Gson;
import com.nilesh.jirahook.entity.Issue;
import com.nilesh.jirahook.entity.JIRAIssue;
import com.nilesh.jirahook.entity.QueueMessage;
import com.nilesh.jirahook.queue.AmazonSQS;
import com.nilesh.jirahook.queue.Queue;
import com.nilesh.jirahook.service.IssueTrackerService;
import com.nilesh.jirahook.service.impl.JiraTrackerService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

public class IssueSumControllerTests {

    private static IssueTrackerService issueTrackerService;
    private static Queue awsQueue;
    private static IssueSumController issueSumController ;

    @BeforeClass
    public static void setup() {
        awsQueue = Mockito.mock(AmazonSQS.class);
        issueTrackerService = Mockito.mock(JiraTrackerService.class);
        List<JIRAIssue> jiraIssues = new ArrayList<>();
        jiraIssues.add(new JIRAIssue("TEST-1", 1));
        jiraIssues.add(new JIRAIssue("TEST-2", 2));
        List<? extends Issue> issues = jiraIssues ;
        Mockito.when(awsQueue.pushMessage("QUERY")).thenReturn(200);
        Mockito.when(awsQueue.pushMessage("FALSE")).thenReturn(404);
        Mockito.doReturn(issues).when(issueTrackerService).getIssues("QUERY");
        Mockito.doReturn(new ArrayList<>()).when(issueTrackerService).getIssues("EMPTY");
        Mockito.doThrow(new RuntimeException("Rest API not available")).when(issueTrackerService).getIssues("EMPTY");
        issueSumController = new IssueSumController(issueTrackerService, awsQueue);
    }

    @Test
    public void sendMessageTest() {
        String Status = issueSumController.sumIssue("QUERY", "QUERY");
        Assert.assertEquals("String is not Success", Status, "Success");
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void sendMessageException() throws RuntimeException {
       expectedEx.expect(RuntimeException.class);
       expectedEx.expectMessage("Rest API not available");
       issueSumController.sumIssue("EMPTY", "EMPTY");
    }

}
