package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RatingResponseDto {
    private Double score;

    public RatingResponseDto(Rating rating) {
        if (rating != null) {
            this.score = rating.getScore();
        }
        if (rating == null) {
            this.score = 0.0;
        }
    }
}
