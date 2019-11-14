package com.myapp.esercitazione.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myapp.esercitazione.postservice.entity.PostEntity;
import com.myapp.esercitazione.postservice.model.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long>{
	// Save con model
	void save(PostModel postInput);	
	/***
	 * @param idUser - ID dello User autore dei post
	 * @return List PostEntity associati all'utenti
	 */
	@Query("SELECT p FROM PostEntity p WHERE p.idUser =:idUser")	
	List<PostEntity> findAllPostsFromUserId(@Param("idUser") long idUser);
}
