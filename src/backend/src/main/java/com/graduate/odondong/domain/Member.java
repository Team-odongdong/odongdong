package com.graduate.odondong.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.graduate.odondong.util.AnimalNameGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Type(type = "uuid-char")
	private UUID uuid;

	private String name;

	@OneToMany(mappedBy = "member")
	private List<Bathroom> bathrooms = new ArrayList<>();

	public Member(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	public static Member createMember() {
		return new Member(UUID.randomUUID(), AnimalNameGenerator.generateRandomName());
	}
}
