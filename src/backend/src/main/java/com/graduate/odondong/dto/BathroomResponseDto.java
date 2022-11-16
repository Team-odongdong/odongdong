package com.graduate.odondong.dto;

import lombok.Builder;
import lombok.Data;

@Data
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

	public BathroomResponseDto (BathroomResponseInterface bathroomResponseInterface, String isOpened) {
		this.bathroomId = bathroomResponseInterface.getBathroomId();
		this.title = bathroomResponseInterface.getTitle();
		this.latitude = bathroomResponseInterface.getLatitude();
		this.longitude = bathroomResponseInterface.getLongitude();
		this.isLocked = bathroomResponseInterface.getIsLocked();
		this.address = bathroomResponseInterface.getAddress();
		this.addressDetail = bathroomResponseInterface.getAddressDetail();
		this.operationTime = bathroomResponseInterface.getOperationTime();
		this.imageUrl = bathroomResponseInterface.getImageUrl();
		this.register = bathroomResponseInterface.getRegister();
		this.isUnisex = bathroomResponseInterface.getIsUnisex();
		this.isOpened = isOpened;
		this.rate = bathroomResponseInterface.getRate();
	}
}
