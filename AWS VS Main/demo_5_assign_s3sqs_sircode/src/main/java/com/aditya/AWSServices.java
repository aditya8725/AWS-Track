package com.aditya;

import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.sqs.*;
import software.amazon.awssdk.regions.Region;


public class AWSServices {
    
    private static final S3Client s3 = S3Client.builder().region(Region.US_EAST_1).build();
    private static final SqsClient sqs = SqsClient.builder().region(Region.US_EAST_1).build();

    public static S3Client s3(){
        return s3;
    }

    public static SqsClient sqs(){
        return sqs;
    }
}