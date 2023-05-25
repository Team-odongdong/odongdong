package com.graduate.odondong.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Member;

import lombok.Data;

@Data
public class MemberProfileResponseDto {

	private String name;
	private String uuid;

	private Integer allRegisterBathroomNum;
	private Integer registerBathroomNum;
	private Integer notRegisterBathroomNum;

	private List<BathroomUpdateRequestDto> registerBathrooms;
	private List<String> notRegisterBathroomTitles;

	public MemberProfileResponseDto(Member member) {
		this.name = member.getName();
		this.uuid = member.getUuid().toString();
		List<Bathroom> memberBathrooms = member.getBathrooms();
		List<Bathroom> registerBathroom = memberBathrooms.stream().filter(Bathroom::getRegister)
			.collect(Collectors.toList());
		List<Bathroom> notRegisterBathroom = memberBathrooms.stream().filter(bathroom -> !bathroom.getRegister())
			.collect(Collectors.toList());

		this.allRegisterBathroomNum = memberBathrooms.size();
		this.registerBathroomNum = registerBathroom.size();
		this.registerBathrooms = registerBathroom.stream().map((bathroom -> BathroomUpdateRequestDto.builder()
			.isLocked(bathroom.getIsLocked())
			.bathroomId(bathroom.getId())
			.address(bathroom.getAddress())
			.isLocked(bathroom.getIsLocked())
			.addressDetail(bathroom.getAddressDetail())
			.isUnisex(bathroom.getIsUnisex())
			.operationTime(bathroom.getOperationTime())
			.title(bathroom.getTitle())
			.latitude(bathroom.getLatitude())
			.longitude(bathroom.getLongitude())
			.imageUrl(bathroom.getImageUrl())
			.build()
		)).collect(Collectors.toList());

		this.notRegisterBathroomNum = notRegisterBathroom.size();
		this.notRegisterBathroomTitles = notRegisterBathroom.stream().map(Bathroom::getTitle).collect(Collectors.toList());
	}
}
