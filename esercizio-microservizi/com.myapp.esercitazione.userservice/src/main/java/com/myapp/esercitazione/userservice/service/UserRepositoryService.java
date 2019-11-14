package com.myapp.esercitazione.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.esercitazione.userservice.entity.UserEntity;
import com.myapp.esercitazione.userservice.mapper.MapperManagement;
import com.myapp.esercitazione.userservice.model.UserModel;
import com.myapp.esercitazione.userservice.repository.UserRepository;

@Service("userRepositoryService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserRepositoryService {
	@Autowired
	UserRepository repository;
	@Autowired
	MapperManagement mapperManagement;		
	public UserModel getUserFromID(UserModel userInput) {
		UserModel ret = null;
		// Controllo la presenza di un user (filtro)
		if (userInput != null) {
			// Controllo che lo user abbia il campo id valorizzato			
			Optional<UserEntity> myGetUser = repository.findById(userInput.getId());
			if (myGetUser.isPresent()) {
				ret = mapperManagement.mappingUserEntityToModel(myGetUser.get());
			}			
		}
		return ret;
	}
	
	public void insertNewUser(UserModel userInput) {
		if (userInput != null) {
			UserEntity myEntity = mapperManagement.mappingUserModelToEntity(userInput);
			repository.save(myEntity);
		}
	}
}
