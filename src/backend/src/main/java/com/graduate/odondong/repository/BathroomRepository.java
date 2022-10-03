package com.graduate.odondong.repository;

import com.graduate.odondong.domain.Bathroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BathroomRepository extends JpaRepository<Bathroom, Long> {

    List<Bathroom> findBathroomsByRegisterIsFalse();
    List<Bathroom> findBathroomsByRegisterIsTrue();
}

