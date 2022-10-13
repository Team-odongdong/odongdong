package com.graduate.odondong.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DeletedBathroom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bathroom_id")
	private Long id;

	private String title;
	private Double latitude;
	private Double longitude;

	private String isLocked;
	private String address;
	private String addressDetail;
	private String imageUrl;
	private Boolean register;
	private Boolean isUnisex;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime created_at;
	@LastModifiedDate
	private LocalDateTime updated_at;

	@Builder
	public DeletedBathroom(Long id, String title, Double latitude, Double longitude, String isLocked, String address,
		String addressDetail, String imageUrl, Boolean register, Boolean isUnisex) {
		this.id = id;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isLocked = isLocked;
		this.address = address;
		this.addressDetail = addressDetail;
		this.imageUrl = imageUrl;
		this.register = register;
		this.isUnisex = isUnisex;
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}
}
