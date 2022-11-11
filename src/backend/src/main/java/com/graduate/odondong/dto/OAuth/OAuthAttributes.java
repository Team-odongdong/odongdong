package com.graduate.odondong.dto.OAuth;

import java.util.Map;

import com.graduate.odondong.domain.User;
import com.graduate.odondong.util.type.Role;

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

	public static OAuthAttributes of(String registrationId,
		String userNameAttributeName,
		Map<String, Object> attributes) {

		if("kakao".equals(registrationId)) {
			return ofKakao("id", attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName,
		Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.picture((String)attributes.get("picture"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName,
		Map<String, Object> attributes) {
		Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

		OAuthAttributes build = OAuthAttributes.builder()
			.name((String)properties.get("nickname"))
			.email((String) kakaoAccount.get("email"))
			.picture((String)properties.get("profile_image"))
			.attributes(properties)
			.nameAttributeKey(userNameAttributeName)
			.build();
		return build;
	}

	public User toEntity() {
		return User.builder()
			.name(name)
			.email(email)
			.picture(picture)
			.role(Role.GUEST)
			.build();
	}
}