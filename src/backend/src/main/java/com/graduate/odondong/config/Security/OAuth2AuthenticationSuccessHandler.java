package com.graduate.odondong.config.Security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final HttpSession httpSession;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		//        login 성공한 사용자 목록.
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

		Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
		String email = (String) kakao_account.get("email");
		Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
		String nickname = (String) properties.get("nickname");
		String session = httpSession.getId();

		String url = makeRedirectUrl(session);
		System.out.println("url: " + url);

		if (response.isCommitted()) {
			logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
			return;
		}
		getRedirectStrategy().sendRedirect(request, response, url);
	}

	private String makeRedirectUrl(String session) {
		return UriComponentsBuilder.fromUriString("https://odongdong.site/oauth2/redirect?session="+session)
			.build().toUriString();
	}
}