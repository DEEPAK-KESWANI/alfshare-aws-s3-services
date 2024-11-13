# AWS S3 Client Application

This application provides a command-line interface for interacting with Amazon S3 (Simple Storage Service). You can perform various operations such as connecting to a bucket, listing objects, downloading files, and uploading files.

## Prerequisites

Before running the application, ensure you have the following:

- **Java Development Kit (JDK)** installed on your machine (version 8 or higher).
- **Maven** installed for building the project. You can check if Maven is installed by running `mvn -v` in your terminal.
- An **AWS account** with access to S3.

## Packaging the Application

To package the application, follow these steps:

1. **Open a Terminal**: Navigate to the root directory of your project where the `pom.xml` file is located. For example:
   ```shell
   cd C:\Data\Projects\alfshare-aws-s3-services
   ```
2. **Build the Application**: Run the following command to clean and package the application:
   ```shell
   mvn clean package
   ```
- `clean`: This command removes any previously compiled files and directories.
- `package`: This command compiles the source code, runs tests, and packages the application into a JAR file.

3. **Locate the Packaged JAR**: After the build process completes successfully, you will find the packaged JAR file in the `target` directory. The JAR file will be named according to the `<artifactId> and <version>` specified in your `pom.xml`. For example:
    ```shell
   target/s3client-0.0.1.jar
   ```
## Running the Packaged Application

To run the packaged application, use the following command format:

```shell
java -jar target/s3client-0.0.1.jar <configFilePath> <operation> [<key> <filePath>]

```


## Configuration

The application requires a properties file that contains the necessary AWS credentials and configuration. Below is the format for the properties file:

```properties
aws.s3.region=my-region
aws.s3.bucketName=my-bucket
aws.s3.accessKey=YOUR_ACCESS_KEY
aws.s3.secretKey=YOUR_SECRET_KEY
```

## Example Properties File
```properties
aws.s3.region=us-west-2
aws.s3.bucketName=my-example-bucket
aws.s3.accessKey=AKIAEXAMPLE
aws.s3.secretKey=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
```

## Usage
To run the application, use the following command format:

```shell
java -jar s3client-<versionNumber>.jar <configFilePath> <operation> [<key> <filePath>]
```

## Operations
The following operations are supported:
- **connect**: Check access to the specified S3 bucket.
```shell
java -jar s3client-0.0.1.jar ./application.properties connect
```
- **list**: List objects in the specified S3 bucket. Optionally, you can provide a path to filter the results.
```shell
  java -jar s3client-0.0.1.jar ./application.properties list
  java -jar s3client-0.0.1.jar ./application.properties list Software
```

- **download**: Download a file from the S3 bucket to a specified local path.
```shell
  java -jar s3client-0.0.1.jar ./application.properties download <key> <filePath>

```

- **upload**: Upload a file from a specified local path to the S3 bucket.
```shell
  java -jar s3client-0.0.1.jar ./application.properties upload <key> <filePath>
```

## Example Usage
To connect to the S3 bucket:
```shell
java -jar s3client-0.0.1.jar ./application.properties connect
```

To list all objects in the bucket:
```shell
java -jar s3client-0.0.1.jar ./application.properties list
```


To download a file from the S3 bucket:
```shell   
java -jar s3client-0.0.1.jar ./application.properties download my-file.txt /local/path/to/save/my-file.txt

```

To upload a file to the bucket:
```shell
java -jar s3client-0.0.1.jar ./application.properties upload my-file.txt /local/path/to/my-file.txt

```

## Notes
- Ensure that the AWS credentials provided in the properties file [(application.properties)]()  have the necessary permissions to perform the specified operations on the S3 bucket.
- Replace `<versionNumber>` with the actual version of your application.
- Replace `<configFilePath>, <operation>, <key> and <filePath>` with the appropriate values for your use case.
- If you encounter any issues during the build process, check the console output for error messages and resolve them accordingly.

## Additional Resources
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/htmlsingle/)
