package com.aditya22;


import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;


//CHEF
public class chef {
    private static final String queueURL = "https://sqs.us-east-2.amazonaws.com/891377117529/myqueuearp";
    public static void main(String[] args) {
        SqsClient client = SqsClient.builder()
                .region(Region.US_EAST_2)
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
        //     System.out.println("Mark as delivered (Delete from the Queue)");
        //     DeleteMessageRequest del = DeleteMessageRequest.builder()
        //                                 .queueUrl(queueURL)
        //                                 .receiptHandle(msg.receiptHandle())
        //                                 .build();
        //     client.deleteMessage(del);
        }
    }
}
