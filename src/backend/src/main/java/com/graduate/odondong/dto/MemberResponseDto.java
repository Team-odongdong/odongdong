package com.graduate.odondong.dto;

import com.graduate.odondong.domain.Member;

import lombok.Data;

@Data
public class MemberResponseDto {

	private String name;
	private String uuid;

	public MemberResponseDto(Member member) {
		this.name = member.getName();
		this.uuid = member.getUuid().toString();
	}
}
