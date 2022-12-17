package com.graduate.odondong.service.OAuth;

import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.OAuth.OAuthAttributes;
import com.graduate.odondong.dto.OAuth.SessionUser;
import com.graduate.odondong.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		if(1==1) throw new RuntimeException("여기 왔다");
		OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = service.loadUser(userRequest); // Oath2 정보를 가져옴

		String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 소셜 정보 가져옴
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		Optional<OAuthAttributes> oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName,
			oAuth2User.getAttributes());
		OAuthAttributes attributes = oAuthAttributes.orElseThrow(
			() -> new OAuth2AuthenticationException("권한 설정을 하지 않았습니다."));

		User user = saveOrUpdate(attributes);

		httpSession.setAttribute("user", new SessionUser(user));

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
			attributes.getAttributes(),
			attributes.getNameAttributeKey());
	}

	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userRepository.findUserByEmail(attributes.getEmail())
			.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
			.orElse(new User(attributes));

		return userRepository.save(user);
	}
}