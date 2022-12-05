package com.graduate.odondong.controller;

import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.dto.RatingResponseDto;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.service.RatingService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/api/bathroom/rating")
    public BaseResponse<String> addBathroomRate(@RequestBody RatingRequestDto ratingRequestDto) {
        try {
            return new BaseResponse<>(ratingService.addRating(ratingRequestDto));
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

//    @GetMapping("/api/bathroom/rating")
//    public BaseResponse<RatingResponseDto> getBathroomRate(@RequestParam("bathroom_id") Long bathroomId) {
//        try {
//            RatingResponseDto ratingResponseDto = new RatingResponseDto(ratingService.retrieveRatingById(bathroomId),bathroomId);
//            return new BaseResponse<>(ratingResponseDto);
//        }
//        catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

}
