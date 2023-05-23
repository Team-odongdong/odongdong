package com.graduate.odondong.repository;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.BathroomResponseInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BathroomRepository extends JpaRepository<Bathroom, Long> {

    List<Bathroom> findBathroomsByRegisterIsFalse();
    List<Bathroom> findBathroomsByRegisterIsTrue();
    void deleteById(Long id);

    @Query("select b.id as bathroomId, b.title as title, b.latitude as latitude, b.longitude as longitude, b.isLocked as isLocked, b.address as address, b.addressDetail as addressDetail, b.operationTime as operationTime, b.imageUrl as imageUrl, b.register as register, b.isUnisex as isUnisex," +
            "avg(coalesce(r.score,0)) as rate from Bathroom b left outer join Rating r " +
            "on r.bathroom = b where b.register = true" +
            " group by b")
    List<BathroomResponseInterface> findAllBathroomResponseDto();

    @Query("select b.id as bathroomId, b.title as title, b.latitude as latitude, b.longitude as longitude, b.isLocked as isLocked, b.address as address, b.addressDetail as addressDetail, b.operationTime as operationTime, b.imageUrl as imageUrl, b.register as register, b.isUnisex as isUnisex," +
            "avg(coalesce(r.score,0)) as rate from Bathroom b left outer join Rating r " +
            "on r.bathroom = b where b.register = true and b.latitude >= :lati_minus and b.latitude <= :lati_plus and b.longitude >= :long_minus and b.longitude <= :long_plus" +
            " group by b")
    List<BathroomResponseInterface> findBathroomResponseDto(@Param("lati_minus")Double x1, @Param("lati_plus")Double x2, @Param("long_minus")Double y1, @Param("long_plus")Double y2);
}
