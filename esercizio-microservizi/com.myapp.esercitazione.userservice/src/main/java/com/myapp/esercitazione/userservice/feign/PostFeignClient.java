package com.myapp.esercitazione.userservice.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.myapp.esercitazione.userservice.model.PostModel;

@FeignClient(name = "post-service", url = "localhost:6222", configuration = ClientConfiguration.class)
public interface PostFeignClient {
	// RICHIAMO IL SERVIZIO RELATIVO ALLA RICERCA DI TUTTI I POST
	@GetMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<PostModel>> getPostFromIdUser(@RequestHeader("userid")Long idUetente);
	// RICHIAMO IL SERVIZIO PER L'INSERIMENTO DEL POST
	@PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PostModel> insertPostFromIdUser(@RequestHeader("userid") Long idUser, @RequestBody PostModel insertPost);
}
