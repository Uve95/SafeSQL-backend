package com.backend.SafeSQL;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan
@EnableJpaRepositories

public class SafeSqlApplication {

	public static void main(String[] args) {

		SpringApplication.run(SafeSqlApplication.class, args);
	}

}

