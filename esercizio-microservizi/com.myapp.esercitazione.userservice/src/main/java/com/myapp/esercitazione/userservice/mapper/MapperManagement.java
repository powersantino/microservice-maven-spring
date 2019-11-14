package com.myapp.esercitazione.userservice.mapper;

import org.mapstruct.Mapper;

import com.myapp.esercitazione.userservice.entity.UserEntity;
import com.myapp.esercitazione.userservice.model.UserModel;

@Mapper(componentModel = "spring")
public abstract class MapperManagement {	
	public abstract UserModel mappingUserEntityToModel(UserEntity entity);
	public abstract UserEntity mappingUserModelToEntity(UserModel model);
}
