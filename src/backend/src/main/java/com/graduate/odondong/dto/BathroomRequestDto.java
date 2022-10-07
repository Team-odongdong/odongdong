package com.graduate.odondong.dto;

import lombok.Data;

@Data
public class BathroomRequestDto {

    private String title;
    private Float latitude;
    private Float longitude;
    private String isLocked;
    private String address;
    private String addressDetail;
    private String imageUrl;
    private Float rate;
}

