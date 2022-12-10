package com.graduate.odondong.service;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.repository.BathroomRepository;
import org.springframework.stereotype.Service;

import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.OAuth.SessionUser;
import com.graduate.odondong.dto.UserProfileResponseDto;
import com.graduate.odondong.repository.UserRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.graduate.odondong.util.BaseResponseStatus.NOT_FOUND_BATHROOM;
import static com.graduate.odondong.util.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final HttpSession httpSession;
	private final UserRepository userRepository;
	private final BathroomRepository bathroomRepository;

	public User getUserInfo() throws BaseException {
		SessionUser sessionUser = Optional.ofNullable((SessionUser)httpSession.getAttribute("user")).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
		return userRepository.findById(sessionUser.getId())
			.orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));
	}
	public BaseResponse<UserProfileResponseDto> findUserProfile() {
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
		//Bathroom bathroom = bathroomRepository.findByUserId(sessionUser.getId());
		return new BaseResponse<>(new UserProfileResponseDto(sessionUser));
	}
}
