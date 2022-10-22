package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Bathroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BathroomResponseDto {

    private Long id;

    private String title;
    private Double latitude;
    private Double longitude;

    private String isLocked;
    private String address;
    private String addressDetail;
    private String imageUrl;
    private Boolean register;
    private String operationTime;
    private Boolean isUnisex;
    private List<RatingResponseDto> rate;


    public BathroomResponseDto(Bathroom bathroom) {
        this.id = bathroom.getId();
        this.title = bathroom.getTitle();
        this.latitude = bathroom.getLatitude();
        this.longitude = bathroom.getLongitude();
        this.isLocked = bathroom.getIsLocked();
        this.address = bathroom.getAddress();
        this.addressDetail = bathroom.getAddressDetail();
        this.imageUrl = bathroom.getImageUrl();
        this.register = bathroom.getRegister();
        this.operationTime = bathroom.getOperationTime();
        this.isUnisex = bathroom.getIsUnisex();
        this.rate = bathroom.getRatings().stream().map(RatingResponseDto::new).collect(Collectors.toList());
    }
}
