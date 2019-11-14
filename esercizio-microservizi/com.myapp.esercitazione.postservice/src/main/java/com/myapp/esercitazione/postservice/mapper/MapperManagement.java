package com.myapp.esercitazione.postservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.myapp.esercitazione.postservice.entity.PostEntity;

import com.myapp.esercitazione.postservice.model.PostModel;

@Mapper(componentModel = "spring")
public abstract class MapperManagement {
	/***
	 * 
	 * @param Post entity
	 * @return Post Model
	 */
	public abstract PostModel mappingPostEntityToModel(PostEntity entity);
	/***
	 * 
	 * @param Post model
	 * @return Post entity
	 */
	public abstract PostEntity mappingPostModelToEntity(PostModel model);	
}
