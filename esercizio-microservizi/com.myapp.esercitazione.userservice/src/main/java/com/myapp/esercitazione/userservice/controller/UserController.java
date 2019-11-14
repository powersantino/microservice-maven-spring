package com.myapp.esercitazione.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.myapp.esercitazione.userservice.feign.PostFeignClient;
import com.myapp.esercitazione.userservice.model.PostModel;
import com.myapp.esercitazione.userservice.model.UserModel;
import com.myapp.esercitazione.userservice.service.UserRepositoryService;

@Controller
public class UserController {
	// Necessario a reperire i POST.
	@Autowired
	UserRepositoryService userRepositoryService;
    // Necesserario a reperire lo USER.
	@Autowired
	PostFeignClient postFeignClient;
	/***
	 * 
	 * @param idUser al quale verrà associato il post
	 * @param insertPost model che rappresenta il post che deve essere salvato
	 * @return User model con tutti i post più il post appena associato.
	 * 		   gli HttpStatus previsti sono: 
	 * 		   
	 */
	@PostMapping(path="/userpost", produces="application/json")
	public ResponseEntity<UserModel> getUserAndPostFromIdUser(@RequestHeader("userId") Long idUser, @RequestBody PostModel insertPost) {
		UserModel ret = new UserModel();
		if (idUser != null) {
			/* La chiamata sul controller Post service
			 * punta a questo servizio e valorizzo ret 
			 * (Il modello di user che ritorna a fine di tutto il giro).
			 */
			UserModel user = new UserModel();
			user.setId(idUser);
			ret = userRepositoryService.getUserFromID(user);			
			/* Se non è nullo:
			 * - Inserisco il POST ARRIVATO.  
			 * - Cerco i post dell'user.
			 * - Setto i post dello user. 			 
			 */
			if (ret != null) {
				// Inserisco il post richiamando il servisio di su POST 
				if(postFeignClient.insertPostFromIdUser(idUser, insertPost).getStatusCode().equals(HttpStatus.CREATED)) {
					// Qui richiamo il serviszio di PostService getPostFromIdUser (Cerco i post dell'user).
					ResponseEntity<List<PostModel>> postUserResp = postFeignClient.getPostFromIdUser(idUser);
					if (postUserResp.getStatusCode().equals(HttpStatus.ACCEPTED)) {
						// Se ritorna uno staus 202 valorizzo i post dell'utente e la relativa lista					
						ret.setPostuser(postUserResp.getBody());
					} else {
						// Tutti gli altri stati, faccio tornare lo status assegnato dal servizio USER 
						return new ResponseEntity<>(ret, postUserResp.getStatusCode());
					}
				}else {
					// Parametri presenti ma ritorna uno status diverso da CREATO
					return new ResponseEntity<>(ret, HttpStatus.SERVICE_UNAVAILABLE);
				}				
			} else {
				// Dati necessari non inviati ritorna un modello nullo e uno status 404 
				return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
			}
		}
		// Tutto OK (202)
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}
}
