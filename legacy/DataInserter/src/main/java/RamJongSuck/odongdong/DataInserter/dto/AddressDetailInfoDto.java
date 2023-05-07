package RamJongSuck.odongdong.DataInserter.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDetailInfoDto {

	String address_name;
	String address_detail;

	@Builder
	public AddressDetailInfoDto(String address_name, String address_detail) {
		this.address_name = address_name;
		this.address_detail = address_detail;
	}
}
