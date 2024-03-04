package com.CalcyIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CalcyIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalcyIntegrationApplication.class, args);
	}

}
