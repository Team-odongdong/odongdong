package com.graduate.odondong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduate.odondong.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByBathroomIdAndMemberId(Long bathroomId, Long memberId);
}
