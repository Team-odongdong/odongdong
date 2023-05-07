package com.graduate.odondong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.graduate.odondong.domain.DeletedBathroom;

public interface DeletedBathroomRepository extends JpaRepository<DeletedBathroom, Long> {
}
