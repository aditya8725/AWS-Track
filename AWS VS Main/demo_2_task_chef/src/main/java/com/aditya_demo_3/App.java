package com.aditya_demo_3;


import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;


//CHEF
public class App {
    private static final String queueURL = "https://sqs.ap-southeast-2.amazonaws.com/891377117529/ARPfood-order";

    // public static void main(String[] args) {
    //     SqsClient client = SqsClient.builder().region(Region.AP_SOUTHEAST_2).build();

    //     System.out.println("Welcome, Chef!");
    //     while (true) {
    //         System.out.println("Waiting for orders...");
    //         receiveMessages(client);
    //         try {
    //             Thread.sleep(5000); // Wait for 5 seconds before checking for new messages
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // static void receiveMessages(SqsClient client) {
    //     ReceiveMessageRequest req = ReceiveMessageRequest.builder()
    //             .queueUrl(queueURL)
    //             .maxNumberOfMessages(5)
    //             .waitTimeSeconds(19)
    //             .visibilityTimeout(2)
    //             .build();

    //     ReceiveMessageResponse resp = client.receiveMessage(req);
    //     List<Message> messages = resp.messages();
    //     if (messages.size() > 0) {
    //         System.out.println("New orders received:");
    //         for (Message msg : messages) {
    //             System.out.println("  " + msg.body());
    //             deleteMessage(client, msg);
    //         }
    //     } else {
    //         System.out.println("No new orders.");
    //     }
    // }

    // static void deleteMessage(SqsClient client, Message message) {
    //     DeleteMessageRequest del = DeleteMessageRequest.builder()
    //             .queueUrl(queueURL)
    //             .receiptHandle(message.receiptHandle())
    //             .build();
    //     client.deleteMessage(del);
    // }


    public static void main(String[] args) {
        SqsClient client = SqsClient.builder()
                .region(Region.AP_SOUTHEAST_2)
                .build();
       
       
        receiveMessage(client);
    }
    static void receiveMessage(SqsClient client){
        ReceiveMessageRequest req = ReceiveMessageRequest.builder()
                                            .queueUrl(queueURL)
                                            .maxNumberOfMessages(5)
                                            .waitTimeSeconds(10)
                                            .build();
       
        ReceiveMessageResponse resp = client.receiveMessage(req);
        List<Message> messages =  resp.messages();
        System.out.println("Found "+messages.size()+" messages");
        for(Message msg : messages){
            System.out.println("Reading the message....");
            System.out.println("  "+msg.body());
            System.out.println("Mark as delivered (Delete from the Queue)");
            DeleteMessageRequest del = DeleteMessageRequest.builder()
                                        .queueUrl(queueURL)
                                        .receiptHandle(msg.receiptHandle())
                                        .build();
            client.deleteMessage(del);
        }
    }
}
