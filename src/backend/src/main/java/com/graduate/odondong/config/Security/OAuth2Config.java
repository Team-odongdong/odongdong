package com.graduate.odondong.config.Security;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Config {

	public OAuth2Config(ApplicationContext context, Map<String, CreateClientSecret> createClientSecretMap) {
		OAuth2ClientProperties properties = context.getBean(OAuth2ClientProperties.class);
		Map<String, OAuth2ClientProperties.Registration> registration = properties.getRegistration();
		OAuth2ClientProperties.Registration apple = registration.get("apple");
		String clientSecret = createClientSecretMap.get("createAppleClientSecret").createClientSecret();
		apple.setClientSecret(clientSecret);
	}
}
