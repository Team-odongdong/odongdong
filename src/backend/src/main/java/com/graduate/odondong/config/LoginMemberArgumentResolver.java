package com.graduate.odondong.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.graduate.odondong.domain.Member;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasLoginAnnotation =
			parameter.hasParameterAnnotation(Login.class);
		boolean hasMemberType =
			Member.class.isAssignableFrom(parameter.getParameterType());
		return hasLoginAnnotation && hasMemberType;
	}

	@Override
	public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return (Member)((HttpServletRequest)webRequest.getNativeRequest()).getAttribute("Member");
	}
}
