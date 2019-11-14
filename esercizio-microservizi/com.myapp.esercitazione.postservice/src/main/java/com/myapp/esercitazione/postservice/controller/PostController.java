package com.myapp.esercitazione.postservice.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.esercitazione.postservice.feign.AuthorizationFeignClient;
import com.myapp.esercitazione.postservice.feign.UserFeignClient;
import com.myapp.esercitazione.postservice.model.PostModel;
import com.myapp.esercitazione.postservice.model.UserModel;
import com.myapp.esercitazione.postservice.service.PostRepositoryService;

@Controller
public class PostController {
	
	@Autowired
	UserFeignClient userFeignClient;	
	@Autowired
	PostRepositoryService postRepositoryService;
	@Autowired 
	AuthorizationFeignClient authorizationFeignClient; 
	
	/***
	 * ENDPOINT RICHIAMATO DAL CLIENT, con questo ENDPOINT parte tutto il processo di verifica USER e inserimento del post nel 
	 * DB collegando l'id dello USER con il POST inserito.
	 * @param idUser - ID dello USER che si deve trovare già all'interno del DB
	 * @param insertPost - Model del post che deve essere insereti a nel DB 
	 * @return UserModel - che è quelo che ha l'ID inviato come parametro e tutta la sua 
	 * 		   lista di post inseriti.
	 */	
	@PostMapping("/postuser")
	public ResponseEntity<UserModel> getUserAndPostFromIdUser(@RequestHeader("userid") Long idUser, @RequestBody PostModel insertPost) {		
		if(idUser!=null && insertPost != null ) {
			// DATI PRESENTI NELLA CHIAMATA, richiamo lo user-service	
			try {
				return userFeignClient.addPostAtUser(idUser, insertPost);	
			} catch (Exception e) {
				// 503
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			}					
		}else {
			// DATI MANCANTI PER LA CHIAMATA (404)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	/*** 
	 * 	ENDPOINT RICHIAMATO DAL FEIGN-CLIENT DEL USER-SEVICE per recuperare tutti post assegnati al' ID dello 
	 *  USER PASSATO in testata. 
	 *  @param Long User id 
	 *  @return List<PostModel> relativi all'id dello USER passato.
	 */
	@GetMapping("/posts")
	public ResponseEntity<List<PostModel>> getAllPostFromIdUser(@RequestHeader("userid") Long idUetente){		
		List<PostModel> myPosts = null;
		ResponseEntity<List<PostModel>> resp;
		if(idUetente!=null) {			
			PostModel serchMod = new PostModel();
			serchMod.setIdUser(idUetente);
			myPosts = postRepositoryService.getPostFromIdUser(serchMod);
			resp = new ResponseEntity<>(myPosts,HttpStatus.ACCEPTED);
		}else {
			// DATI MANCANTI PER LA CHIAMATA (404)
			resp = new ResponseEntity<>(myPosts,HttpStatus.BAD_REQUEST);
		}	
		return resp;
	}	
	/***
	 *  ENDPOINT CHE VIENE RICHIAMATO DAL FEIGN-CLIENT DEL USER-SERVICE per inserire il post a DB 
	 * 	@param RequestHeader idUser, ID dello USER (DEVE ESSERE GIA' PRESENTE NEL DB).
	 * 	@param RequestBody insertPost, Model del post che deve essere insereto nel DB.
	 *  @return PostModel - Model del post appena inserito. 
	 */
	 @PostMapping("/post")
	 public ResponseEntity<PostModel> insertPostFromIdUser(@RequestHeader("userid") Long idUser, @RequestBody PostModel insertPost) {
		 PostModel ret = null;
		 if(idUser!=null && insertPost != null) {
			 ret = postRepositoryService.insertNewPost(idUser,insertPost);
		 }else {
			 return new ResponseEntity<>(ret,HttpStatus.BAD_REQUEST);
		 }
		 return new ResponseEntity<>(ret,HttpStatus.CREATED);
	 }
	 @PostMapping("/login")
	 public ResponseEntity<Object> login( @RequestParam("username") String username, @RequestParam("password") String password) {	               	
	    ResponseEntity<String> ret =  authorizationFeignClient.getTokenFromUserPassword(username, password);
	    if(ret.getStatusCode() == HttpStatus.UNAUTHORIZED) {
	    	return ResponseEntity.status(UNAUTHORIZED).body("Creadenziali errate!");
	    }else {
	    	return ResponseEntity.status(HttpStatus.OK).body(ret.getBody());
	    }
	 }
	 
}
