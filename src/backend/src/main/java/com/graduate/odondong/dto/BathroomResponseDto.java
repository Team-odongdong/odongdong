package com.graduate.odondong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BathroomResponseDto {

	Long bathroomId;
	String title;
	Double latitude;
	Double longitude;
	String isLocked;
	String address;
	String addressDetail;
	String operationTime;
	String imageUrl;
	Boolean register;
	Boolean isUnisex;
	String isOpened;
	Double rate;
}
