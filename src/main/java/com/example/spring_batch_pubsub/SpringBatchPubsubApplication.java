package com.example.spring_batch_pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchPubsubApplication {

	public static void main(String[] args) {
		System.out.println("Starting Spring Boot application...");
		SpringApplication.run(SpringBatchPubsubApplication.class, args);
	}

}
