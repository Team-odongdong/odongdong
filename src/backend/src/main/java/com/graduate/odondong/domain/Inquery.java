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

@Data
@Entity
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Inquery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquery_id")
    private Long id;

    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public Inquery(Long id, String content, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
