package com.example.HR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.HR.repository")
public class HrManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrManagementSystemApplication.class, args);
	}

}
