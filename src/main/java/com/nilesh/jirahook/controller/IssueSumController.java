package com.nilesh.jirahook.controller;

import com.nilesh.jirahook.entity.Issue;
import com.nilesh.jirahook.entity.QueueMessage;
import com.nilesh.jirahook.queue.Queue;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nilesh.jirahook.service.IssueTrackerService;

import java.util.List;

@Log4j2
@RestController
public class IssueSumController {

    private IssueTrackerService issueTrackerService;
    private Queue awsQueue;

    public IssueSumController(@Autowired IssueTrackerService issueTrackerService, @Autowired Queue awsQueue){
        this.issueTrackerService = issueTrackerService ;
        this.awsQueue = awsQueue ;
    }

    @RequestMapping("api/issue/sum")
    public String greeting(@RequestParam(value="query") String query, @RequestParam(value = "name") String name) {
        try {
            List<Issue> issues = issueTrackerService.getIssues(query);
            int result = 0;
            for (Issue issue : issues) {
                result += issue.getStoryPoints();
            }
            QueueMessage queueMessage = new QueueMessage(name, result);
            awsQueue.pushMessage("String");
            return "Success";
        } catch (Exception ex){
            log.error("Get Sum API Failed", ex);
        }
        return "Success" ;
    }
}
