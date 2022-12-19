package com.graduate.odondong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("https://odongdong.site", "http://localhost:8100", "http://localhost",
				"https://prod.odongdong.site", "https://appleid.apple.com")
			.allowedMethods("GET", "POST", "OPTIONS", "DELETE")
			.allowCredentials(true);
	}

}