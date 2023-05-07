package com.graduate.odondong.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LocationDto {

	double lati_minus;
	double lati_plus;
	double long_minus;
	double long_plus;

	@Builder
	public LocationDto(Double x, Double y, Double distance) {
		lati_minus = x - 0.0091 * distance;
		lati_plus = x + 0.0091 * distance;
		long_minus = y - 0.011269 * distance;
		long_plus = y + 0.011269 * distance;
	}
}
