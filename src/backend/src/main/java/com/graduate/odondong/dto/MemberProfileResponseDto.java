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

	private List<String> registerBathroomTitles;
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
		this.registerBathroomTitles = registerBathroom.stream().map(Bathroom::getTitle).collect(Collectors.toList());

		this.notRegisterBathroomNum = notRegisterBathroom.size();
		this.notRegisterBathroomTitles = notRegisterBathroom.stream().map(Bathroom::getTitle).collect(Collectors.toList());
	}
}
