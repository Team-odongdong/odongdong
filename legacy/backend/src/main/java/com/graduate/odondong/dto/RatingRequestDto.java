package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.domain.User;
import lombok.Data;

@Data
public class RatingRequestDto {
    private Double score;
    private Long bathroomId;

    public Rating toRating(Bathroom bathroom, User user) {
        return Rating.builder()
                .score(score)
                .bathroom(bathroom)
                .user(user)
                .build();
    }
}
