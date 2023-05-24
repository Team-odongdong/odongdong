package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Member;
import com.graduate.odondong.domain.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RatingResponseDto {
    private Double score;

    private String name;
    private String uuid;
    public RatingResponseDto(Rating rating, Member member) {
        this.name = member.getName();
        this.uuid = member.getUuid().toString();
        if (rating != null) {
            this.score = rating.getScore();
        }
        if (rating == null) {
            this.score = 0.0;
        }
    }
}
