package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Member;
import com.graduate.odondong.domain.Rating;

import lombok.Data;

@Data
public class RatingRequestDto {
    private Double score;
    private Long bathroomId;

    public Rating toRating(Bathroom bathroom, Member member) {
        return Rating.builder()
                .score(score)
                .bathroom(bathroom)
                .member(member)
                .build();
    }
}
