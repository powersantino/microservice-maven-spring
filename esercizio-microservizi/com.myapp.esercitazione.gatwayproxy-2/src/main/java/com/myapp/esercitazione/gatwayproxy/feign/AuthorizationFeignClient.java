package com.myapp.esercitazione.gatwayproxy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "authorization-service", url = "localhost:6223", configuration = ClientConfiguration.class)
public interface AuthorizationFeignClient {		
	@PostMapping(value="/token",consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Object> validateToken(@RequestParam("token") String token);	
}
