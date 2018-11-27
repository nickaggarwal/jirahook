package com.nilesh.jirahook.service;

import com.nilesh.jirahook.entity.Issue;
import com.nilesh.jirahook.entity.JIRAIssue;
import com.nilesh.jirahook.service.impl.JiraTrackerService;
import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JiraTrackerServiceTests {

    private static JiraTrackerService jiraTrackerService ;
    private static RestTemplate restTemplate;

    @Before
    public void setup(){
        if (jiraTrackerService == null) {
            System.setProperty("JIRA_URL", "http://localhost:8000");
            restTemplate = Mockito.mock(RestTemplate.class);
            ResponseEntity<String> responseEntity = ResponseEntity.ok(
                    "[{ \"issueKey\": \"TEST-1\", \"fields\": { \"storyPoints\": 1 } }," +
                            "{ \"issueKey\": \"TEST-2\", \"fields\": { \"storyPoints\": 2 } }]");
            Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(Class.class))).thenReturn(responseEntity);
            jiraTrackerService = new JiraTrackerService(restTemplate);
        }

    }

    @Test
    public void getAllIssues() {

        List<JIRAIssue> jiraIssues = new ArrayList<>();
        jiraIssues.add(new JIRAIssue("TEST-1", 1));
        jiraIssues.add(new JIRAIssue("TEST-2", 2));
        List<? extends Issue> issues = jiraTrackerService.getIssues("Query");
        Assert.assertEquals("failure - size are not equal" , issues.size(), jiraIssues.size());
        Assert.assertEquals("failure - issue 1 not equal", issues.get(0), issues.get(0));
        Assert.assertEquals("failure - issue 1 not equal", issues.get(1), issues.get(1));
    }
}
