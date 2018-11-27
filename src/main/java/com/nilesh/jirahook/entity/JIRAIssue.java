package com.nilesh.jirahook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Data
public class JIRAIssue implements Issue {

    private String issueKey ;
    private Integer storyPoints ;

    @Override
    public String getIssueName() {
        return issueKey;
    }

    public static List<JIRAIssue> getIssueFromJson(String json) throws ParseException{
        JSONParser parser = new JSONParser();
        List<JIRAIssue> issues = new ArrayList<>();
        JSONArray array = (JSONArray)parser.parse(json);
        Iterator<JSONObject> issueObjectList = array.listIterator();
        while (issueObjectList.hasNext()){
            JSONObject obj = issueObjectList.next();
            String issueName = obj.get("issueKey").toString();
            Integer storyPoint = Integer.parseInt(((JSONObject)obj.get("fields")).get("storyPoints").toString());
            JIRAIssue jiraIssue = new JIRAIssue(issueName, storyPoint);
            issues.add(jiraIssue);
        }
        return issues;
    }
}
