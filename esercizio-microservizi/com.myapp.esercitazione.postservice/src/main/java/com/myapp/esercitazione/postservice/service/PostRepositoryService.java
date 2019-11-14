package com.myapp.esercitazione.postservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.esercitazione.postservice.repository.PostRepository;
import com.myapp.esercitazione.postservice.entity.PostEntity;
import com.myapp.esercitazione.postservice.mapper.MapperManagement;
import com.myapp.esercitazione.postservice.model.PostModel;

@Service("postRepositoryService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PostRepositoryService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	MapperManagement mapperManagement;	
	
	public List<PostModel> getPostFromIdUser(PostModel postFinder){
		List<PostModel> retList = null;
		if(postFinder!=null) {
			List<PostEntity> retEntityList = postRepository.findAllPostsFromUserId(postFinder.getIdUser());
			if(retEntityList!=null && !retEntityList.isEmpty()) {
				retList = new ArrayList<>();				
				for(PostEntity e  : retEntityList){
					retList.add(mapperManagement.mappingPostEntityToModel(e));
				}
			}
		}
		return retList;
	}
	
	public PostModel insertNewPost(Long idUser, PostModel postInsertModel){
		PostModel retModel = null;
		if(idUser!=null && postInsertModel!=null) {
			postInsertModel.setIdUser(idUser);
			PostEntity entity = mapperManagement.mappingPostModelToEntity(postInsertModel);
			PostEntity retEntity = postRepository.save(entity);
			if(retEntity!=null) {
				retModel = mapperManagement.mappingPostEntityToModel(retEntity);
			}			
		}
		return retModel;
	}
}
