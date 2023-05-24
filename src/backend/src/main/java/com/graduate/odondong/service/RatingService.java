package com.graduate.odondong.service;

import static com.graduate.odondong.util.BaseResponseStatus.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Member;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.dto.MemberResponseDto;
import com.graduate.odondong.dto.RatingRequestDto;
import com.graduate.odondong.dto.RatingResponseDto;
import com.graduate.odondong.repository.BathroomRepository;
import com.graduate.odondong.repository.RatingRepository;
import com.graduate.odondong.util.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RatingService {
    private final BathroomRepository bathroomRepository;
    private final RatingRepository ratingRepository;

    private final MemberService memberService;
    public MemberResponseDto addRating(RatingRequestDto ratingRequestDto, Member member) throws BaseException {

        Bathroom bathroom = bathroomRepository.findById(ratingRequestDto.getBathroomId()).orElseThrow(() -> new BaseException(NOT_FOUND_BATHROOM));
        Rating rating = findRatingById(ratingRequestDto.getBathroomId(), member.getId());

        rating = Optional.ofNullable(rating).orElseGet(() -> ratingRequestDto.toRating(bathroom, member));
        rating.setScore(ratingRequestDto.getScore());
        ratingRepository.save(rating);
        return new MemberResponseDto(member);
    }

    public RatingResponseDto findBathroomRateById(Long bathroomId, Long userId) {
        Rating rating = findRatingById(bathroomId, userId);
        return new RatingResponseDto(rating);
    }

    public Rating findRatingById(Long bathroomId, Long userId) {
        return ratingRepository.findByBathroomIdAndMemberId(bathroomId, userId).orElseGet(() -> null);
    }

}
