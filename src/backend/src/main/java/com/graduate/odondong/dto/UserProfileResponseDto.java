package com.graduate.odondong.dto;

import com.graduate.odondong.dto.OAuth.SessionUser;

import lombok.Data;

@Data
public class UserProfileResponseDto {

	private String name;
	private String email;
	private String picture;

	public UserProfileResponseDto(SessionUser sessionUser) {
		this.name = sessionUser.getName();
		this.email = sessionUser.getEmail();
		this.picture = sessionUser.getPicture();
	}
}
