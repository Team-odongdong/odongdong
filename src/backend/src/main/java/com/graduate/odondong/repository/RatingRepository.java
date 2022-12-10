package com.graduate.odondong.repository;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByBathroomIdAndUserId(Long bathroomId, Long userId);
}
