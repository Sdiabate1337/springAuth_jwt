package com.example.sprintAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SprintAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprintAuthApplication.class, args);
	}
}
