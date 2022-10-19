package com.graduate.odondong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OdondongApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdondongApplication.class, args);
	}
}
