package com.graduate.odondong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graduate.odondong.dto.OAuth.SessionUser;
import com.graduate.odondong.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final HttpSession httpSession;

	@ResponseBody
	@GetMapping(value = "/test")
	public String kakaoOauthRedirect(HttpServletRequest httpServletRequest) throws JSONException {
		HttpSession httpSession1 = httpSession;
		SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
		return sessionUser.getEmail();
	}

	@GetMapping(value = "/login")
	public String loginPage(Model model) throws JSONException {
		return "login";
	}
}
