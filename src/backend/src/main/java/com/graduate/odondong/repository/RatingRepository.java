package com.graduate.odondong.repository;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
