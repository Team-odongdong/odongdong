package com.graduate.odondong.controller;

import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduate.odondong.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/login/oauth2/code")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;


	@GetMapping(value = "/kakao")
	public String kakaoOauthRedirect(@RequestParam String code) throws JSONException {
		return userService.getUserInfoByRestAPI(code);
	}
}
