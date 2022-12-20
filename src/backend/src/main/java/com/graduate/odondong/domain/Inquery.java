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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    public Inquery(String content, String email) {
        this.content = content;
        this.email = email;
    }
}
