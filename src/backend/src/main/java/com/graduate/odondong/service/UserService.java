package com.graduate.odondong.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.OAuth.SessionUser;
import com.graduate.odondong.dto.UserProfileResponseDto;
import com.graduate.odondong.repository.UserRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final HttpSession httpSession;
	private final UserRepository userRepository;

	public User getUserInfo() {
		SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
		return userRepository.findById(sessionUser.getId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID));
	}

	public BaseResponse<UserProfileResponseDto> getUserProfile() {
		SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
		return new BaseResponse<>(new UserProfileResponseDto(sessionUser));
	}
}
