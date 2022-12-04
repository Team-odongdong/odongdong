package com.graduate.odondong.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class FilterChainProxyAdvice {

	@Around("execution(public void org.springframework.security.web.FilterChainProxy.doFilter(..))")
	public void handleRequestRejectedException (ProceedingJoinPoint pjp) throws Throwable {
		try {
			pjp.proceed();
		} catch (RequestRejectedException exception) {
			HttpServletRequest request = (HttpServletRequest)pjp.getArgs()[0];
			String ip = request.getHeader("X-Forwarded-For");
			if (ip == null) ip = request.getRemoteAddr();
			log.error("ip 주소는 " + ip + "입니다");
		}
	}
}

