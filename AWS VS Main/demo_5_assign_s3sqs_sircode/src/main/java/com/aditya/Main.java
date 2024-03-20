package com.aditya;

// import software.amazon.awssdk.core.sync.RequestBody;
// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.s3.*;
// import software.amazon.awssdk.services.s3.model.*;
// import software.amazon.awssdk.services.sqs.*;
// import software.amazon.awssdk.services.sqs.model.*;
 
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.Scanner;
 
// public class Main {
 
//     private static final String BUCKET_NAME = "shubham-219885";
//     private static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/891377117529/shubham219885";
 
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
 
//         // Accept a single line of text from the user
//         System.out.print("Enter a line of text: ");
//         String userInput = scanner.nextLine();
 
//         // Generate a timestamp for the object key
//         String timestamp = new SimpleDateFormat("yyyy-dd-MM-HH-mm-ss").format(new Date());
//         String objectKey = timestamp + ".txt";
 
//         // Upload the text to S3
//         String s3ObjectUrl = uploadToS3(userInput, objectKey);
 
//         // Send a message to SQS
//         sendMessageToSQS(s3ObjectUrl);
 
//         System.out.println("Process completed successfully.");
//     }
 
//     private static String uploadToS3(String text, String objectKey) {
//         S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
 
//         PutObjectResponse response = s3Client.putObject(PutObjectRequest.builder()
//                 .bucket(BUCKET_NAME)
//                 .key(objectKey)
//                 .build(), RequestBody.fromString(text));
 
//         return s3Client.utilities().getUrl(GetUrlRequest.builder()
//                 .bucket(BUCKET_NAME)
//                 .key(objectKey)
//                 .build()).toExternalForm();
//     }
 
//     private static void sendMessageToSQS(String s3ObjectUrl) {
//         SqsClient sqsClient = SqsClient.builder().region(Region.US_EAST_1).build();
 
//         SendMessageResponse response = sqsClient.sendMessage(SendMessageRequest.builder()
//                 .queueUrl(QUEUE_URL)
//                 .messageBody("S3 Object URL: " + s3ObjectUrl)
//                 .build());
 
//         System.out.println("Message sent to SQS. Message ID: " + response.messageId());
//     }
// }


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class Main {
    private static final String QUEUE_URL="https://sqs.us-east-1.amazonaws.com/890756660068/mahendra-assign";
    private static final String S3_BUCKET="mahendra-assign";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the message : ");
        String line = sc.nextLine();

        LocalDateTime now = LocalDateTime.now();
        String key = now.format(DateTimeFormatter.ofPattern("yyyyddMMhhmmss"));
        System.out.println("The timestamp : "+key);
        key = key +".txt";
        AWSServices.s3().putObject(
                    PutObjectRequest.builder()
                        .key(key)
                        .bucket(S3_BUCKET)
                        .contentType("text/plain").build(),
                    RequestBody.fromString(line));
        ///// OPTIONAL : Adding Attributes            
        Map<String, MessageAttributeValue> attributes = new HashMap<>();
        attributes.put("objectkey", MessageAttributeValue.builder().dataType("String").stringValue(key).build());
        /////

        AWSServices.sqs().sendMessage(SendMessageRequest
                                .builder()
                                .queueUrl(QUEUE_URL)
                                .messageBody("Successfuly uploaded an Object "+key)
                                .messageAttributes(attributes)
                                .build());
        System.out.println("Message sent !");
    }
}