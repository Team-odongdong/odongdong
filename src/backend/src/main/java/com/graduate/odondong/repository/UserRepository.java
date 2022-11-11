package com.graduate.odondong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduate.odondong.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);
	// Optional<User> findByEmail(String email);
}
