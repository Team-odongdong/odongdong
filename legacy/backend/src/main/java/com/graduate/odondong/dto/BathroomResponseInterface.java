package com.graduate.odondong.dto;

public interface BathroomResponseInterface {
    Long getBathroomId();
    String getTitle();
    Double getLatitude();
    Double getLongitude();
    String getIsLocked();
    String getAddress();
    String getAddressDetail();
    String getOperationTime();
    String getImageUrl();
    Boolean getRegister();
    Boolean getIsUnisex();
    Double getRate();
}
