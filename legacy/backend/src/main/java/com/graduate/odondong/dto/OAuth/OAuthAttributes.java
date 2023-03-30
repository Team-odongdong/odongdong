package com.graduate.odondong.dto.OAuth;

import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes,
		String nameAttributeKey, String name,
		String email, String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	public static Optional<OAuthAttributes> of(String registrationId,
		String userNameAttributeName,
		Map<String, Object> attributes) {

		if ("kakao".equals(registrationId)) {
			return ofKakao(userNameAttributeName, attributes);
		}
		else if ("apple".equals(registrationId)) {
			return ofApple(userNameAttributeName, attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static Optional<OAuthAttributes> ofGoogle(String userNameAttributeName,
		Map<String, Object> attributes) {
		return Optional.of(OAuthAttributes.builder()
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.picture((String)attributes.get("picture"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build());
	}

	private static Optional<OAuthAttributes> ofKakao(String userNameAttributeName,
		Map<String, Object> attributes) {
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

		if (!properties.containsKey("nickname") || !kakaoAccount.containsKey("email") || !properties.containsKey(
			"profile_image"))
			return Optional.empty();

		OAuthAttributes build = OAuthAttributes.builder()
			.name((String)properties.get("nickname"))
			.email((String)kakaoAccount.get("email"))
			.picture((String)properties.get("profile_image"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
		return Optional.of(build);
	}

	private static Optional<OAuthAttributes> ofApple(String userNameAttributeName,
		Map<String, Object> attributes) {
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

		if (!properties.containsKey("nickname") || !kakaoAccount.containsKey("email") || !properties.containsKey(
			"profile_image"))
			return Optional.empty();

		OAuthAttributes build = OAuthAttributes.builder()
			.name((String)properties.get("nickname"))
			.email((String)kakaoAccount.get("email"))
			.picture((String)properties.get("profile_image"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
		return Optional.of(build);
	}
}