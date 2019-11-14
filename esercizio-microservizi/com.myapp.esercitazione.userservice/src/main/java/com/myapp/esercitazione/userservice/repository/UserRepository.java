package com.myapp.esercitazione.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.esercitazione.userservice.entity.UserEntity;
import com.myapp.esercitazione.userservice.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	// Save con model
	void save(UserModel userInput);	
	
}
