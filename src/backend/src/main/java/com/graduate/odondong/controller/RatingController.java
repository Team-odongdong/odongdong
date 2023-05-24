package com.graduate.odondong.controller;

import static com.graduate.odondong.util.ErrorLogWriter.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduate.odondong.config.Login;
import com.graduate.odondong.domain.Member;
import com.graduate.odondong.dto.MemberResponseDto;
import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.dto.RatingResponseDto;
import com.graduate.odondong.service.RatingService;
import com.graduate.odondong.service.UserService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;

import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/rating")
public class RatingController {

	private final RatingService ratingService;
	private final UserService userService;

	@GetMapping("")
	public BaseResponse<RatingResponseDto> findBathroomRate(HttpServletRequest request,
		@RequestParam("id") Long bathroomId) {
		Long userId;
		try {
			userId = userService.getUserInfo().getId();
		} catch (BaseException e) {
			writeExceptionWithRequest(e, request);
			return new BaseResponse<>(e.getStatus());
		}
		return new BaseResponse<>(ratingService.findBathroomRateById(bathroomId, userId));
	}


	@PostMapping("")
	public BaseResponse<MemberResponseDto> addBathroomRate(@RequestBody RatingRequestDto ratingRequestDto, @ApiIgnore @Login Member member) {
		return new BaseResponse<>(ratingService.addRating(ratingRequestDto, member));
	}
}
