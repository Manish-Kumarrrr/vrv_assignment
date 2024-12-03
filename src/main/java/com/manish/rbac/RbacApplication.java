package com.manish.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the RBAC (Role-Based Access Control) application.
 * This class contains the main method to launch the Spring Boot application.
 */


@SpringBootApplication // This annotation enables auto-configuration, component scanning, and allows the application to run as a Spring Boot app.
public class RbacApplication {

	/**
	 * The main method that starts the Spring Boot application.
	 * @param args Command line arguments passed when the application is run.
	 */
	public static void main(String[] args) {
		// Run the Spring Boot application with the specified configuration class.
		SpringApplication.run(RbacApplication.class, args);

	}
}
