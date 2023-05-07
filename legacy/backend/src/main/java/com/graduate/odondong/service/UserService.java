package com.graduate.odondong.service;

import static com.graduate.odondong.util.BaseResponseStatus.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.OAuth.SessionUser;
import com.graduate.odondong.dto.UserProfileResponseDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.UserRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final HttpSession httpSession;
	private final UserRepository userRepository;
	private final BathroomRepository bathroomRepository;

	public User getUserInfo() throws BaseException {
		SessionUser sessionUser = Optional.ofNullable((SessionUser)httpSession.getAttribute("user")).orElseThrow(() -> new BaseException(USER_NOT_LOGIN));
		return userRepository.findById(sessionUser.getId())
			.orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));
	}

	public String getUserEmail() throws BaseException {
		return Optional.ofNullable((SessionUser)httpSession.getAttribute("user"))
			.map(SessionUser::getEmail)
			.orElse(null);
	}

	public BaseResponse<UserProfileResponseDto> findUserProfile() {
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
		return new BaseResponse<>(new UserProfileResponseDto(sessionUser));
	}
}
