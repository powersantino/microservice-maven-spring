package com.myapp.esercitazione.authorization.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.esercitazione.authorization.model.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, UUID>{
	Optional<UserLog> findByUsername(String username);
    Optional<UserLog> findByToken(String token);
}
