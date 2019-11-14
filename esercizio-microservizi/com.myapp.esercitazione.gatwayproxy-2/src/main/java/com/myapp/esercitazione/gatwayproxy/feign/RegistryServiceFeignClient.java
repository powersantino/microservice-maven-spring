package com.myapp.esercitazione.gatwayproxy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "registry-service", url = "localhost:6228", configuration = ClientConfiguration.class)
public interface RegistryServiceFeignClient {
//	@RequestMapping("/service-instances/{applicationName}")
//	ServiceInstance getInstance( @PathVariable String applicationName);
	@RequestMapping("/service-instances/{applicationName}")
	String getInstanceURI(@PathVariable String applicationName);
	@RequestMapping("/service-instances-port/{applicationName}")
	int getInstancePort(@PathVariable String applicationName);
}
