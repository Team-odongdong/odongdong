package com.graduate.odondong.config;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.graduate.odondong.domain.Member;
import com.graduate.odondong.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		String authentication = request.getHeader("Authorization");
		if (authentication != null) {
			Member member = memberService.registerOrLoginMemberUUID(UUID.fromString(authentication));
			request.setAttribute("Member", member);
			return true;
		}
		Member member = memberService.registerMember();
		request.setAttribute("Member", member);
		return true;
	}

}
