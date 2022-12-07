package RamJongSuck.odongdong.DataInserter.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressInfoDto {

    Double latitude;
    Double longitude;
    String address;
    String address_detail;

    @Builder
    public AddressInfoDto(Double latitude, Double longitude, String address, String address_detail) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.address_detail = address_detail;
    }
}
