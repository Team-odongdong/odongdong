package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDto {
    private Double score;

    public RatingResponseDto(Rating rating){
        this.score = rating.getScore();
    }
}
