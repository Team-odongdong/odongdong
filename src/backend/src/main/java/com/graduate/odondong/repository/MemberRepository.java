package com.graduate.odondong.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.graduate.odondong.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUuid(UUID uuid);

	@Query("SELECT m FROM Member m LEFT JOIN FETCH m.bathrooms")
	Optional<Member> findByUuidWithBathrooms(UUID uuid);
}
