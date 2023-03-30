package com.graduate.odondong.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.graduate.odondong.dto.OAuth.OAuthAttributes;
import com.graduate.odondong.util.type.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column
	private String picture;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Builder
	public User(String name, String email, String picture, Role role) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}

	public User(OAuthAttributes oAuthAttributes) {
		this.name = oAuthAttributes.getName();
		this.email = oAuthAttributes.getEmail();
		this.picture = oAuthAttributes.getPicture();
		this.role = Role.GUEST;
	}

	public User update(String name, String picture) {
		this.name = name;
		this.picture = picture;
		return this;
	}

	public String getRoleKey() {
		return this.role.getKey();
	}
}