package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Member;
import com.graduate.odondong.domain.UpdatedBathroom;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    @Builder
    public BathroomUpdateRequestDto(Long bathroomId, Long userId, String title, Double latitude, Double longitude, String isLocked, String address, String addressDetail, String imageUrl,String operationTime, Boolean isUnisex) {
        this.bathroomId = bathroomId;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLocked = isLocked;
        this.address = address;
        this.addressDetail = addressDetail;
        this.imageUrl = imageUrl;
        this.operationTime = operationTime;
        this.isUnisex = isUnisex;
    }


    public UpdatedBathroom toUpdatedBathroom(Bathroom bathroom, Member member) {
        return UpdatedBathroom.builder()
                .title(title)
                .bathroom(bathroom)
                .member(member)
                .latitude(latitude)
                .longitude(longitude)
                .isLocked(isLocked)
                .address(address)
                .addressDetail(addressDetail)
                .register(false)
                .imageUrl(imageUrl)
                .operationTime(operationTime)
                .isUnisex(isUnisex)
                .build();
    }
}
