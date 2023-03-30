package com.graduate.odondong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextListener;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class OdondongApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdondongApplication.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}
