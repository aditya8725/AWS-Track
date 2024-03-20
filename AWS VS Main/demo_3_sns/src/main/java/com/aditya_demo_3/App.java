package com.aditya_demo_3;

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
