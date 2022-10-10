package com.graduate.odondong.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordinateInfoDto {

    String address_name;
    String address_detail;

    @Builder
    public CoordinateInfoDto(String address_name, String address_detail) {
        this.address_name = address_name;
        this.address_detail = address_detail;
    }
}
