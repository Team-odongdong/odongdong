package com.graduate.odondong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:9003", "http://localhost:3000", "http://127.0.0.1:3000", "https://odongdong.site"
			,"http://localhost:8081", "http://localhost:8100", "http://localhost:8000")
			.allowedMethods("GET", "POST", "OPTIONS")
			.allowCredentials(true);
	}

}