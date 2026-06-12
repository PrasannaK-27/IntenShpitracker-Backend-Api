package com.Backend.JobTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
public class JobTrackerApplication {

	public static void main(String[] args) {
//		createUploadFolder();
		SpringApplication.run(JobTrackerApplication.class, args);
	}

//	static void createUploadFolder() {
//		try {
//			Path uploadDir = Paths.get("uploads");
//			if (!Files.exists(uploadDir)) {
//				Files.createDirectories(uploadDir);
//				System.out.println("uploads/ folder created ✅");
//			} else {
//				System.out.println("uploads/ folder already exists ✅");
//			}
//		} catch (Exception e) {
//			System.out.println("Failed to create uploads/ folder: " + e.getMessage());
//		}
//	}

}
