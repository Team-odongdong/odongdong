package com.graduate.odondong.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class Bathroom {

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
	private String operationTime;
	private Boolean isUnisex;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime created_at;
	@LastModifiedDate
	private LocalDateTime updated_at;

	@Builder
	public Bathroom(Long id, String title, Double latitude, Double longitude, String isLocked, String address,
		String addressDetail, String imageUrl, Boolean register, Boolean isUnisex, Double rate, String operationTime) {
		this.id = id;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isLocked = isLocked;
		this.address = address;
		this.addressDetail = addressDetail;
		this.operationTime = operationTime;
		this.imageUrl = imageUrl;
		this.register = register;
		this.isUnisex = isUnisex;
	}

	public void update(UpdatedBathroom updatedBathroom) {
		this.title = updatedBathroom.getTitle();
		this.latitude = updatedBathroom.getLatitude();
		this.longitude = updatedBathroom.getLongitude();
		this.isLocked = updatedBathroom.getIsLocked();
		this.address = updatedBathroom.getAddress();
		this.addressDetail = updatedBathroom.getAddressDetail();
		this.operationTime = updatedBathroom.getOperationTime();
		this.imageUrl = updatedBathroom.getImageUrl();
		this.register = updatedBathroom.getRegister();
		this.isUnisex = updatedBathroom.getIsUnisex();
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}
}
