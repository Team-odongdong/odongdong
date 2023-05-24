package com.graduate.odondong.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.graduate.odondong.util.operationTime.OperationTimeValidation;

import lombok.Builder;
import lombok.Data;

@Data
public class BathroomResponseDto {

	private List<BathroomInfo> bathroomInfoList;

	@Data
	@Builder
	public static class BathroomInfo {
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
		Double distance;

		public BathroomInfo withDistance(double calculateDistance) {
			this.setDistance(calculateDistance * 1000);
			return this;
		}

	}

	public BathroomResponseDto(List<BathroomResponseInterface> bathrooms) {
		this.bathroomInfoList = bathrooms.stream().map(
			(data) -> BathroomInfo.builder()
				.bathroomId(data.getBathroomId())
				.latitude(data.getLatitude())
				.longitude(data.getLongitude())
				.operationTime(data.getOperationTime())
				.rate(data.getRate())
				.address(data.getAddress())
				.addressDetail(data.getAddressDetail())
				.title(data.getTitle())
				.imageUrl(data.getImageUrl())
				.isLocked(data.getIsLocked())
				.register(data.getRegister())
				.isUnisex(data.getIsUnisex())
				.isOpened(OperationTimeValidation.checkBathroomOpen(data.getOperationTime()))
				.build()
		).collect(Collectors.toList());
	}
}
