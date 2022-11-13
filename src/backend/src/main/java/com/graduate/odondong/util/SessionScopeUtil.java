package com.graduate.odondong.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.graduate.odondong.dto.OAuth.SessionUser;

public class SessionScopeUtil {

	public static HttpServletRequest getCurrReq(){
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	public static void setSession(Object o){
		HttpSession httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
			.getSession();
		httpSession.setAttribute("user", o);
	}

	public static SessionUser getSession(Object o){
		HttpSession httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
			.getSession();
		return (SessionUser)httpSession.getAttribute("user");
	}
}