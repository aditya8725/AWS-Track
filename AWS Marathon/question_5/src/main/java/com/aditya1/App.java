package com.aditya1;


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Scanner;

public class App {
    private static final String Topic_ARN = "arn:aws:sns:ap-southeast-2:891377117529:newoffersarp";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your message: ");
        String msg = sc.nextLine();

        SnsClient client = SnsClient.builder().region(Region.AP_SOUTHEAST_2).build();

        System.out.println("Sending a message to your registered email address!");

        PublishRequest req = PublishRequest.builder()
                .targetArn(Topic_ARN)
                .subject("New Offers")
                .message("Your message is: " + msg + "!")
                .build();

        client.publish(req);
        System.out.println("Successfully sent....!");


        sc.close();
    }
}