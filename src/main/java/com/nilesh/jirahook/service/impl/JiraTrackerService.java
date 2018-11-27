package com.nilesh.jirahook.service.impl;

import com.nilesh.jirahook.entity.Issue;
import com.nilesh.jirahook.entity.JIRAIssue;
import com.nilesh.jirahook.service.IssueTrackerService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class JiraTrackerService implements IssueTrackerService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${JIRA_URL}")
    private String endpoint;

    private final String api = "/rest/api/2/search" ;

    @Override
    public List<? extends Issue> getIssues(String Query) {
        List<? extends Issue> issueList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String result = restTemplate.exchange(endpoint + api, HttpMethod.GET, entity, String.class).getBody();
        try {
            issueList = JIRAIssue.getIssueFromJson(result) ;
        } catch (ParseException ex){
            log.error("Could not convert Issues from Rest API", ex);
        }
        return issueList;
    }

}
