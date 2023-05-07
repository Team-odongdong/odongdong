package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import com.graduate.odondong.domain.UpdatedBathroom;
import lombok.Data;

@Data
public class BathroomRequestDto {

    private String title;
    private Double latitude;
    private Double longitude;
    private String isLocked;
    private String address;
    private String addressDetail;
    private String imageUrl;
    private Boolean isUnisex;
    private Double rate;

    public Bathroom toBathroom(String bathroomImgUrl) {
        return Bathroom.builder()
                .title(title)
                .latitude(latitude)
                .longitude(longitude)
                .isLocked(isLocked)
                .address(address)
                .addressDetail(addressDetail)
                .register(false)
                .imageUrl(bathroomImgUrl)
                .isUnisex(isUnisex)
                .build();
    }

    public Rating toRating(Bathroom bathroom) {
        return Rating.builder()
                .score(rate)
                .bathroom(bathroom)
                .build();
    }
}
