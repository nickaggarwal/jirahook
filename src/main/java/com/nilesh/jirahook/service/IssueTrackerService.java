package com.nilesh.jirahook.service;

import com.nilesh.jirahook.entity.Issue;

import java.util.List;

public interface IssueTrackerService {
    public List<Issue> getIssues(String Query);
}
