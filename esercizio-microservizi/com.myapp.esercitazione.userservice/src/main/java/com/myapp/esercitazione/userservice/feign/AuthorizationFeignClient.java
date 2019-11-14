package com.myapp.esercitazione.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authorization-service", url = "localhost:6223", configuration = ClientConfiguration.class)
public interface AuthorizationFeignClient {
	@PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE )
	ResponseEntity<String> getTokenFromUserPassword(@RequestHeader("username") String username,
			@RequestHeader("password") String password);
	@PostMapping(value="/token",consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Object> validateToken(@RequestHeader("token") String token);
	@PostMapping(value="/logout",consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Object> logOut(@RequestHeader("username") String username);
}
