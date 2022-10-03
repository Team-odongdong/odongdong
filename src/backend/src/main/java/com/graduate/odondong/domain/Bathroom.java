package com.graduate.odondong.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Float latitude;
    private Float longitude;

    private String isLocked;
    private String address;
    private String addressDetail;
    private String imageUrl;
    private Boolean register;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public Bathroom(Long id, String title, Float latitude, Float longitude, String isLocked, String address, String addressDetail, String imageUrl, Boolean register) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLocked = isLocked;
        this.address = address;
        this.addressDetail = addressDetail;
        this.imageUrl = imageUrl;
        this.register = register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }
}