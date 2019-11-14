package com.myapp.esercitazione.postservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.esercitazione.postservice.model.PostModel;
import com.myapp.esercitazione.postservice.model.UserModel;

@FeignClient(name = "user-service", url = "localhost:6221", configuration = ClientConfiguration.class)
public interface UserFeignClient {		
	@RequestMapping("/userpost")
	ResponseEntity<UserModel> addPostAtUser(@RequestHeader("userid") Long idUetente,@RequestBody PostModel post);	
}



