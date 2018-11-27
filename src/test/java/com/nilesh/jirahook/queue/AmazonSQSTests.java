package com.nilesh.jirahook.queue;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import com.nilesh.jirahook.entity.QueueMessage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class AmazonSQSTests {

    private static AmazonSQS amazonSQS ;
    private static com.amazonaws.services.sqs.AmazonSQS client;
    protected static String queueUrl ;


    @BeforeClass
    public static void setup(){
        System.setProperty("QUEUE_URL", "default-queue");
        client = Mockito.mock(com.amazonaws.services.sqs.AmazonSQS.class);
        HttpResponse httpResponse = new HttpResponse(null, null, null);
        httpResponse.setStatusCode(200);
        SendMessageResult sendMessageResult = new SendMessageResult();
        sendMessageResult.setSdkHttpMetadata(SdkHttpMetadata.from(httpResponse));
        Mockito.when(client.sendMessage(new SendMessageRequest(queueUrl, "{\"name\":\"hello\",\"totalPoints\":3}"))).thenReturn(sendMessageResult);
        SendMessageResult sendMessageResult1 = new SendMessageResult();
        httpResponse.setStatusCode(404);
        sendMessageResult1.setSdkHttpMetadata(SdkHttpMetadata.from(httpResponse));
        Mockito.when(client.sendMessage(new SendMessageRequest(queueUrl, "empty"))).thenReturn(sendMessageResult1);
        amazonSQS = new AmazonSQS(queueUrl, client);
    }

    @Test
    public void sendMessageSuccess() {
        QueueMessage queueMessage = new QueueMessage("hello", 3);
        Gson gson = new Gson() ;
        int status = amazonSQS.pushMessage(gson.toJson(queueMessage));
        Assert.assertEquals("Message Not Sent", status, 200);

    }

    @Test
    public void sendMessageFail() {
       int status = amazonSQS.pushMessage("empty");
       Assert.assertEquals("Sending should have failed", status, 404);
    }
}
