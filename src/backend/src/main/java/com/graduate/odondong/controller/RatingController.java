package com.graduate.odondong.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.dto.RatingResponseDto;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.RatingService;
import com.graduate.odondong.service.UserService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/rating")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;

    @GetMapping("")
    public BaseResponse<RatingResponseDto> findBathroomRate(HttpServletRequest request, @RequestParam("id") Long bathroomId) {
        Long userId;
        try {
            userId = userService.getUserInfo().getId();
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        return new BaseResponse<>(ratingService.findBathroomRateById(bathroomId, userId));
    }

    @PostMapping("")
    public BaseResponse<String> addBathroomRate(@RequestBody RatingRequestDto ratingRequestDto) {
        User user;
        try {
            user = userService.getUserInfo();
            return new BaseResponse<>(ratingService.addRating(ratingRequestDto,user));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
