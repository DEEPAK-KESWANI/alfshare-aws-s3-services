package com.alfshare.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;

@SpringBootApplication
public class S3Application implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {

		SpringApplication.run(S3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (args.length < 2) {
			String properties = "\r\naws.s3.region=my-region \r\n"
					+ "aws.s3.bucketName=my-bucket\r\n"
					+ "aws.s3.accessKey=YOUR_ACCESS_KEY\r\n"
					+ "aws.s3.secretKey=YOUR_SECRET_KEY";
			System.err
					.println("Please provide the path to the properties file (say /home/user/application.properties).");
			System.err.println("Properties Format: " + properties);
			System.err.println(
					"Usage: java -jar s3client-<versionNumber>.jar <configFilePath> <operation> [<key> <filePath>]");
			System.err.println("Usage: java -jar s3client-0.0.1.jar ./application.properties connect");
			System.err.println("Usage: java -jar s3client-0.0.1.jar ./application.properties list");
			System.err.println("Usage: java -jar s3client-0.0.1.jar ./application.properties list Software");

			return;
		}

		System.out.println("Number of Arguments: " + args.length);

		// "--spring.config.location=file:" + args[0]
		String configLocation = "--spring.config.location=file:" + args[0];
		String operation = args[1];

		if (ObjectUtils.isEmpty(configLocation) || ObjectUtils.isEmpty(operation)) {
			System.err
					.println("Usage: java -jar s3client-<version>.jar <configFilePath> <operation> [<key> <filePath>]");
			return;
		}

		switch (operation.toLowerCase()) {

			case "connect":

				s3Service.hasAccessToBucket();
				break;

			case "list":
				if (args.length < 2) {
					System.err.println("Usage: java -jar s3client-<version>.jar <configFilePath> list [<filePath>]");
					return;
				}

				String path = "";
				if (args.length == 3) {
					path = args[2];
				}

				s3Service.listObjectsInBucket(path);

				break;

			case "download":
				if (args.length < 4) {
					System.err.println(
							"Usage: java -jar s3client-<version>.jar <configFilePath> download <key> <filePath>");
					return;
				}

				String downloadKey = args[3];
				String downloadFilePath = args[4];

				s3Service.downloadFileFromBucket(downloadKey, downloadFilePath);
				break;

			case "upload":
				if (args.length < 4) {
					System.err.println(
							"Usage: java -jar s3client-<version>.jar <configFilePath> upload <key> <filePath>");
					return;
				}

				String uploadKey = args[3];
				String uploadFilePath = args[4];

				s3Service.uploadFileToBucket(uploadKey, uploadFilePath);
				break;

			default:
				System.err.println("Invalid operation: " + operation);
				System.err.println(
						"Usage: java -jar s3client.jar <accessKey> <secretKey> <region> <operation> [<bucketName> <key> <filePath>]");

				break;
		}
	}

}
