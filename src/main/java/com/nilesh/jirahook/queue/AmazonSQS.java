package com.nilesh.jirahook.queue;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AmazonSQS implements Queue {

    private com.amazonaws.services.sqs.AmazonSQS client;

    @Value("${QUEUE_URL}")
    private String queueUrl;

    public AmazonSQS(){
        this.client = AmazonSQSClientBuilder.defaultClient();
    }

    public AmazonSQS(String queueUrl, com.amazonaws.services.sqs.AmazonSQS amazonSQS){
        this.client = amazonSQS;
        this.queueUrl = queueUrl ;
    }

    @Override
    public int pushMessage(String message) {
        System.out.println("Sending Message : " + message);
        final SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl,message);
        SendMessageResult sendMessageResult = client.sendMessage(sendMessageRequest);
        return sendMessageResult.getSdkHttpMetadata().getHttpStatusCode();
    }

    // To Do : Not Implement
    @Override
    public List<String> getMessage() {
        return null;
    }
}