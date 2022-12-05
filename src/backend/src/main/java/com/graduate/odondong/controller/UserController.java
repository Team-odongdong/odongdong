package com.graduate.odondong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graduate.odondong.dto.UserProfileResponseDto;
import com.graduate.odondong.service.UserService;
import com.graduate.odondong.util.BaseResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@ResponseBody
	@GetMapping(value = "/profile")
	public BaseResponse<UserProfileResponseDto> kakaoOauthRedirect(){
		return userService.findUserProfile();
	}

	@GetMapping(value = "/login")
	public String loginPage(){
		return "login";
	}
}
