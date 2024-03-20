package com.aditya22;


import java.util.Scanner;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class App 
{
private static final String queueURL = "https://sqs.us-east-2.amazonaws.com/891377117529/myqueuearp";

    public static void main(String[] args) {
        SqsClient client = SqsClient.builder().region(Region.US_EAST_2).build();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Aditya's Restaurant!");
        System.out.print("Enter food item (e.g., rice, biryani, roti): ");
        String foodItem = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter table number (1-10): ");
        int tableNumber = scanner.nextInt();

        String orderMessage = String.format("Order \"%s\" for \"%d\" on table \"%d\"", foodItem, quantity, tableNumber);
        sendMessage(client, orderMessage);

        System.out.println("Your order has been placed successfully!");
        scanner.close();
    }

    static void sendMessage(SqsClient client, String message) {
        SendMessageRequest req = SendMessageRequest.builder()
                .queueUrl(queueURL)
                .messageBody(message)
                .delaySeconds(2)
                .build();
        client.sendMessage(req);
        System.out.println("Order sent successfully!");
    }
}
