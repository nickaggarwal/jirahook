package com.nilesh.jirahook.service;

import com.nilesh.jirahook.entity.Issue;

import java.util.List;

public interface IssueTrackerService {
    public List<? extends Issue> getIssues(String Query);
}
