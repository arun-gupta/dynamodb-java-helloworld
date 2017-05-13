package org.sample.aws.dynamodb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * @author arungupta
 */
public class App {

	public static void main(String[] args) {
		App app = new App();
		app.createBook();
		System.out.println("JSON document stored");
		Book book = app.getBook();
		System.out.println("Found document: " + book);
	}
	
	private static AmazonDynamoDB dynamodbClient;
	
	private static AmazonDynamoDB getClient() {
		if (null != dynamodbClient)
			return dynamodbClient;
		
		String region = System.getProperty("DYNAMODB_REGION");
		if (null == region) {
			System.err.println("Region not set, default \"" + Regions.US_WEST_1.name() + "\" is used");
			region = Regions.US_WEST_1.name();
		}
		System.out.println("DynamoDB region: " + region);
		
		dynamodbClient = AmazonDynamoDBClientBuilder.standard()
	            .withRegion(region)
	            .build();
		
		return dynamodbClient;
	}
	
	private void createBook() {
		DynamoDBMapper mapper = new DynamoDBMapper(getClient());
		Book book = new Book("1", "Minecraft Modding with Forge", "1234", "29.99");
		mapper.save(book);
	}
	
	private Book getBook() {
		DynamoDBMapper mapper = new DynamoDBMapper(getClient());
		return mapper.load(Book.class, "1");
	}
	
}
