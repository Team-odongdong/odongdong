package com.graduate.odondong.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService {

	@Value("${kakao.REST_API_KEY}")
	private String clientId;
	@Value("${kakao.CLIENT_SECRET}")
	private String clientSecret;

	public String getUserInfoByRestAPI(String code) throws JSONException {
		WebClient webClient = WebClient.builder()
			.build();

		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("grant_type", "authorization_code");
		multiValueMap.add("client_id", clientId);
		multiValueMap.add("redirect_uri", "http://localhost:9003/login/oauth2/code/kakao");
		multiValueMap.add("code", code);
		multiValueMap.add("client_secret", clientSecret);

		String KakaoResponse = webClient
			.post()
			.uri("https://kauth.kakao.com/oauth/token")
			.body(BodyInserters.fromFormData(multiValueMap))
			.retrieve()
			.bodyToMono(String.class)
			.block();

		JSONObject jsonObject = new JSONObject(KakaoResponse);
		String access_token = jsonObject.getString("access_token");

		String authorization = webClient
			.post()
			.uri("https://kapi.kakao.com/v2/user/me")
			.headers((httpHeaders -> {
				httpHeaders.add("Authorization", "Bearer " + access_token);
				httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			}))
			.retrieve()
			.bodyToMono(String.class)
			.block();
		return authorization;
	}
}
