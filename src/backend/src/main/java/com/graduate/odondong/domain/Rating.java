package com.graduate.odondong.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double score;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bathroom_id")
    private Bathroom bathroom;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public Rating(Long id, Double score, Bathroom bathroom, Member member) {
        this.id = id;
        this.score = score;
        this.bathroom = bathroom;
        this.member = member;
    }


}
