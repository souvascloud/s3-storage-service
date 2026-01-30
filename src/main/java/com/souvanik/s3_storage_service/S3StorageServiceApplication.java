package com.souvanik.s3_storage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class S3StorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3StorageServiceApplication.class, args);
	}

}
