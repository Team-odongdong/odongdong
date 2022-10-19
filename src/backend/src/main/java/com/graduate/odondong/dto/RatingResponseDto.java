package com.graduate.odondong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDto {
    private Double score;
    private Long bathroomId;
}
