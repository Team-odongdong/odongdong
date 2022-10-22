package com.graduate.odondong.repository;

import com.graduate.odondong.domain.Bathroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BathroomRepository extends JpaRepository<Bathroom, Long> {

    List<Bathroom> findBathroomsByRegisterIsFalse();
    List<Bathroom> findBathroomsByRegisterIsTrue();
    void deleteById(Long id);

    List<Bathroom> findByRegisterIsTrueAndLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(Double x1, Double x2, Double y1, Double y2);
}
