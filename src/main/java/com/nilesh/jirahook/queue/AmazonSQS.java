package com.nilesh.jirahook.queue;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AmazonSQS implements Queue {

    @Override
    public int pushMessage(String message) {
        System.out.println("Sending Message : " + message);
        return 1;
    }

    @Override
    public List<String> getMessage() {
        return null;
    }
}