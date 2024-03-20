package com.aditya5;
import java.util.Random;
import java.util.Scanner;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

public class App 
{
    private static final String Topic_ARN = "arn:aws:sns:ap-southeast-2:891377117529:news219861";
    public static void main( String[] args )
    {
       int random = (int)(Math.random() * 50 + 1);

        // Random random = new Random();

        // // Generate a 4-digit random number
        // int randomNumber = random.nextInt(9000) + 1000;



        SnsClient client = SnsClient.builder().region(Region.AP_SOUTHEAST_2).build();

        System.out.println("Sending an OTP to your registered email address !");

        PublishRequest req = PublishRequest.builder()
                                    .targetArn(Topic_ARN)
                                    .subject("Your OTP")
                                    .message("Your OTP is "+random+" . Kindly do not share this OTP with anyone !")
                                    .build();
        
        client.publish(req);
        Scanner sc = new Scanner(System.in);
        System.out.println("Kindly enter your OTP: ");
        String otp = sc.nextLine();

        int otpFromMail = Integer.parseInt(otp);
        if(random == otpFromMail){
            System.out.println("Authentication is successful !");
        }else{
            System.out.println("Invalid OTP ?");
        }
        sc.close();
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