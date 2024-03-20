package com.aditya5;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

public class App 
{
    private static final String Topic_ARN = "arn:aws:sns:us-east-1:891377117529:news61";
    public static void main( String[] args )
    {
        String msg ="Hello world";
        SnsClient client = SnsClient.builder().region(Region.US_EAST_1).build();

        System.out.println("Sending an msg to your registered email address !");

        PublishRequest req = PublishRequest.builder()
                                    .targetArn(Topic_ARN)
                                    .subject("Your MSG")
                                    .message("Your msg is "+msg+" !")
                                    .build();
        
        client.publish(req);
        System.out.println("Message: " + msg);

    }
    
}


/*

package com.aditya5;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Scanner; // Import the Scanner class

public class App {
    private static final String Topic_ARN = "arn:aws:sns:us-east-1:891377117529:news61";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        System.out.print("Enter your message: ");
        String msg = scanner.nextLine(); // Read user input

        SnsClient client = SnsClient.builder().region(Region.US_EAST_1).build();

        System.out.println("Sending a message to your registered email address!");

        PublishRequest req = PublishRequest.builder()
                .targetArn(Topic_ARN)
                .subject("Your Message")
                .message("Your message is: " + msg + "!")
                .build();

        client.publish(req);
        System.out.println("Message: " + msg);
    }
}


 */