package com.graduate.odondong.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.dto.RatingResponseDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.RatingRepository;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.graduate.odondong.util.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RatingService {
    private final BathroomRepository bathroomRepository;
    private final RatingRepository ratingRepository;
    public String createRating(RatingRequestDto ratingRequestDto) throws BaseException {
        try {
            Rating rating = Rating.builder()
                    .score(ratingRequestDto.getScore())
                    .bathroom(bathroomRepository.findById(ratingRequestDto.getBathroomId()).get())
                    .userId(ratingRequestDto.getUserId())
                    .build();
            ratingRepository.save(rating);
            return "SUCCESS";
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Double retrieveRatingById(Long bathroomId) throws BaseException
    {
        double tmp = 3.1;
        return tmp;
    }


}
