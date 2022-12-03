package com.graduate.odondong.repository;

import com.graduate.odondong.domain.UpdatedBathroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpdatedBathroomRepository extends JpaRepository<UpdatedBathroom, Long> {

    List<UpdatedBathroom> findUpdatedBathroomsByRegisterIsFalse();

}
