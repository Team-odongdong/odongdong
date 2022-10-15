package com.graduate.odondong.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
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
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double score;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bathroom_id")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Bathroom bathroom;
    private Long userId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public Rating(Long id, Double score, Long bathroomId, Long userId) {
        this.id = id;
        this.score = score;
        this.bathroomId = bathroomId;
        this.userId = userId;
    }


}
