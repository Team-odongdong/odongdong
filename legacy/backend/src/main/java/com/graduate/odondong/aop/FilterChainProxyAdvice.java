package com.graduate.odondong.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import static com.graduate.odondong.util.ErrorLogWriter.writeExceptionWithAopRequest;

@Aspect
@Component
@Slf4j
public class FilterChainProxyAdvice {

	@Around("execution(public void org.springframework.security.web.FilterChainProxy.doFilter(..))")
	public void handleRequestRejectedException(ProceedingJoinPoint pjp) throws Throwable {
		try {
			pjp.proceed();
		} catch (RequestRejectedException e) {
			HttpServletRequest request = (HttpServletRequest)pjp.getArgs()[0];
			writeExceptionWithAopRequest(e, request);
		}
	}
}

