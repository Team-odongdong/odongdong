package com.graduate.odondong.config.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateClientSecretConfig {

	@Bean
	public CreateClientSecret createAppleClientSecret() {
		return new CreateAppleClientSecret();
	}
}
