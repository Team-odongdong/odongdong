package com.graduate.odondong.dto;

import lombok.Data;

@Data
public class BathroomUpdateRequestDto {

    private Long bathroomId;
    private String title;
    private Double latitude;
    private Double longitude;
    private String isLocked;
    private String address;
    private String addressDetail;
    private String imageUrl;
    private String operationTime;
    private Boolean isUnisex;

}
