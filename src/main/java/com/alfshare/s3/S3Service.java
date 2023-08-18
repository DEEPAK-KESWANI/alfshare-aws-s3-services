package com.alfshare.s3;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class S3Service {

	private final AmazonS3 s3client;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	public S3Service(@Value("${aws.s3.accessKey}") String accessKey, @Value("${aws.s3.secretKey}") String secretKey,
			@Value("${aws.s3.region}") String region) {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(region).build();
	}

	public boolean hasAccessToBucket() {
		try {
			s3client.listObjects(bucketName);
			System.out.println("---------------------------");
			System.out.println(" Checking Access to Bucket.... ");
			System.out.println("Has access to Bucket: " + bucketName);
			System.out.println("---------------------------");
			return true;
		} catch (AmazonServiceException e) {
			System.out.println("AmazonServiceException: " + e.getMessage());
			return false;
		} catch (AmazonClientException e) {
			System.out.println("AmazonClientException: " + e.getMessage());
			return false;
		}
	}

	public List<S3ObjectSummary> listObjectsInBucket(String path) {

		if (hasAccessToBucket()) {

			ObjectListing objectListing = ObjectUtils.isEmpty(path) ? s3client.listObjects(bucketName)
					: s3client.listObjects(bucketName, path);

			// List objects in a bucket
			List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
			System.out.println("---------------------------");
			System.out.println(" Getting List from Bucket.... ");
			for (S3ObjectSummary objectSummary : objectSummaries) {
				System.out.println("Object key: " + objectSummary.getKey());
			}
			System.out.println("---------------------------");
			return objectListing.getObjectSummaries();
		}

		return Collections.emptyList();

	}

	public void downloadFileFromBucket(String key, String outputFilePath) {
		try {
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, key));
			S3ObjectInputStream inputStream = s3object.getObjectContent();
			FileUtils.copyInputStreamToFile(inputStream, new File(outputFilePath));

			System.out.println("---------------------------");
			System.out.println("File downloaded to: " + outputFilePath);
			System.out.println("---------------------------");

		} catch (AmazonServiceException e) {
			System.out.println("AmazonServiceException: " + e.getMessage());
		} catch (AmazonClientException e) {
			System.out.println("AmazonClientException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	public void uploadFileToBucket(String key, String uploadPath) {
		try {
			s3client.putObject(new PutObjectRequest(bucketName, key, new File(uploadPath)));

			System.out.println("---------------------------");
			System.out.println("File uploaded to: " + key);
			System.out.println("---------------------------");

		} catch (AmazonServiceException e) {
			System.out.println("AmazonServiceException: " + e.getMessage());
		} catch (AmazonClientException e) {
			System.out.println("AmazonClientException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

}
