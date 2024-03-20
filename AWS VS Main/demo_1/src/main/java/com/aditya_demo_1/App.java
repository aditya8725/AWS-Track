// package com.aditya_demo_1;

// import java.util.List;

// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.sqs.SqsClient;
// import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
// import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;

// public class App 
// {
//     public static void main( String[] args )
//     {
//         SqsClient client = SqsClient.builder().region(Region.AP_SOUTHEAST_2).build();

//         ListQueuesRequest req = ListQueuesRequest.builder().build();

//         ListQueuesResponse resp = client.listQueues(req);

//         List<String> queuerul = resp.queueUrls();

//         System.out.println("You have "+queuerul.size()+ " queues");

//         queuerul.forEach(qurl -> System.out.println("Queue URL: "+qurl));
//     }
// }

package com.aditya_demo_1;

import java.util.List;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import software.amazon.awssdk.services.sqs.model.Message;

public class App {
    //sqs url
    private static final String queueURL = "https://sqs.ap-southeast-2.amazonaws.com/891377117529/AdityaR";

    public static void main(String[] args) {
        SqsClient client = SqsClient.builder().region(Region.AP_SOUTHEAST_2).build();

        // List all Queues
        listQueues(client);

        // send Meaasge from java code to aws
        sendMessage(client, "Hello World from Aditya..!");

        // receive msg from aws to java code
        System.out.println("Start reading messages ...");
        receiveMessage(client);
    }

    static void listQueues(SqsClient client) 
    {
        ListQueuesRequest req = ListQueuesRequest.builder().build();
        ListQueuesResponse resp = client.listQueues(req);

        List<String> queueUrls = resp.queueUrls();
        System.out.println("You have " + queueUrls.size() + " queues");
        queueUrls.forEach(qUrl -> System.out.println("Queue Url : " + qUrl));
    }

    static void sendMessage(SqsClient client, String message) 
    {
        SendMessageRequest req = SendMessageRequest.builder().queueUrl(queueURL).messageBody(message).delaySeconds(2).build();
        client.sendMessage(req);
        System.out.println("Message sent successfully...... !");
    }

    static void receiveMessage(SqsClient client) {
        ReceiveMessageRequest req = ReceiveMessageRequest.builder()
                .queueUrl(queueURL)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(19)
                .visibilityTimeout(2)
                .build();

        ReceiveMessageResponse resp = client.receiveMessage(req);
        List<Message> messages = resp.messages();
        System.out.println("Found " + messages.size() + " messages");
        for (Message msg : messages) {
            System.out.println("Reading the message....");
            System.out.println("  " + msg.body());
            System.out.println("Mark as delivered (Delete from the Queue)");
            DeleteMessageRequest del = DeleteMessageRequest.builder()
                    .queueUrl(queueURL)
                    .receiptHandle(msg.receiptHandle())
                    .build();
            client.deleteMessage(del);
        }
    }
}
