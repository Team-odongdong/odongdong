package com.graduate.odondong.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.domain.User;
import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.dto.RatingResponseDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.RatingRepository;
import com.graduate.odondong.repository.UserRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.graduate.odondong.util.BaseResponseStatus.DATABASE_ERROR;
import static com.graduate.odondong.util.BaseResponseStatus.NOT_FOUND_BATHROOM;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RatingService {
    private final BathroomRepository bathroomRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    public String addRating(RatingRequestDto ratingRequestDto, User user) throws BaseException {
        Bathroom bathroom = bathroomRepository.findById(ratingRequestDto.getBathroomId()).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        Rating rating = findRatingById(ratingRequestDto.getBathroomId(), user.getId());

        rating = Optional.ofNullable(rating).orElseGet(() -> ratingRequestDto.toRating(bathroom, user));
        rating.setScore(ratingRequestDto.getScore());
        ratingRepository.save(rating);
        return "SUCCESS";
    }

    public RatingResponseDto findBathroomRateById(Long bathroomId, Long userId) {
        Rating rating = findRatingById(bathroomId, userId);
        return new RatingResponseDto(rating);
    }

    public Rating findRatingById(Long bathroomId, Long userId) {
        return ratingRepository.findByBathroomIdAndUserId(bathroomId, userId).orElseGet(() -> null);
    }

}
