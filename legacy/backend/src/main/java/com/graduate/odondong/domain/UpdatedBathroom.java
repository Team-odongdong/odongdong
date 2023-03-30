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

@Entity()
@Data
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UpdatedBathroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bathroom_id")
    private Bathroom bathroom;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;
    private String title;
    private Double latitude;
    private Double longitude;
    private String isLocked;
    private String address;
    private String addressDetail;
    private String imageUrl;
    private String operationTime;
    private Boolean isUnisex;
    private Boolean register;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public UpdatedBathroom(Long id, User user, Bathroom bathroom, String title, Double latitude, Double longitude, String isLocked, String address, String addressDetail, String imageUrl, String operationTime, Boolean isUnisex, Boolean register) {
        this.id = id;
        this.user = user;
        this.bathroom = bathroom;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLocked = isLocked;
        this.address = address;
        this.addressDetail = addressDetail;
        this.imageUrl = imageUrl;
        this.operationTime = operationTime;
        this.isUnisex = isUnisex;
        this.register = register;
    }


}