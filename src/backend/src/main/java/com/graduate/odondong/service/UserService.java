package com.graduate.odondong.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.graduate.odondong.dto.OAuth.SessionUser;
import com.graduate.odondong.dto.UserProfileResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final HttpSession httpSession;

	public UserProfileResponseDto getUserProfile() {
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
		return new UserProfileResponseDto(sessionUser);
	}
}
