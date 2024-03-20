package com.aditya5;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.sqs.*;
import software.amazon.awssdk.services.sqs.model.*;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
 
public class App {
 
    private static final String BUCKET_NAME = "adityaar-assign";
    private static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/891377117529/adityaar-queue";
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        // Accept a single line of text from the user
        System.out.print("Enter a line of text: ");
        String userInput = scanner.nextLine();
 
        // Generate a timestamp for the object key
        String timestamp = new SimpleDateFormat("yyyy-dd-MM-HH-mm-ss").format(new Date());
        String objectKey = timestamp + ".txt";
 
        // Upload the text to S3
         uploadToS3(userInput, objectKey);
 
        // Send a message to SQS
        sendMessageToSQS(objectKey);
 
        System.out.println("Process completed successfully.");
        scanner.close();
    }
 
    private static void uploadToS3(String text, String objectKey) {
        S3Client s3Client = S3Client.builder().region(Region.US_EAST_2).build();
 
        PutObjectResponse response = s3Client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(objectKey)
                .build(), RequestBody.fromString(text));
 
      
    }
 
    private static void sendMessageToSQS(String objectKey) {
        SqsClient sqsClient = SqsClient.builder().region(Region.US_EAST_1).build();
 
        SendMessageResponse response = sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .messageBody("S3 Object URL: " + objectKey)
                .build());
 
        System.out.println("Message sent to SQS. Message ID: " + response.messageId());
    }
}