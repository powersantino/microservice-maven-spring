package com.myapp.esercitazione.registry.controller;

import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.myapp.esercitazione.registry.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
@EnableDiscoveryClient
@RestController
public class InterceptCallController {
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@RequestMapping("/**")
	public ResponseEntity<Object> routingPublicEndPoitService(){
		//DiscoveryClient discoveryClient = new DiscoveryClient();
		
		System.out.println("DENTRO INTERCEPTOR <<==");
		HttpServletRequest request = 
		        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
		                .getRequest();
		String path = request.getContextPath();
		// Qui faccio il routing a partire dal contesto
		List<String> registrdServiceId = discoveryClient.getServices();
		System.out.println("Dopo get servizi registrati");
		System.out.println(registrdServiceId.size()+"");		
		// --------------------------------------------------------------------------------
		// TEST ---------------------------------------------------------------------------
		List<ServiceInstance> urlService2 = discoveryClient.getInstances("post-service");
		//discoveryClient . getInstances ( "Eureka Client Example" ) ; 
		ServiceInstance service2 = urlService2.get(0);
		System.out.println(service2.getHost() + " Host");
		System.out.println(service2.getPort() + " Porta");
		System.out.println(service2.getScheme() + " Schema");
		System.out.println(service2.getUri() + " Uri");
		// --------------------------------------------------------------------------------
		// TEST 2 -------------------------------------------------------------------------
		PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
	    Applications applications = registry.getApplications();

	    applications.getRegisteredApplications().forEach((registeredApplication) -> {
	        registeredApplication.getInstances().forEach((instance) -> {
	            System.out.println(instance.getAppName() + " (" + instance.getInstanceId() + ") : ");
	        });
	    });
		// --------------------------------------------------------------------------------
		
	    for (String id : registrdServiceId) {
	    	System.out.println(id);
			if(path.contains(id)) {
				List<ServiceInstance> urlService = discoveryClient.getInstances(id);
				ServiceInstance service = urlService.get(0);				
				URI serviceURI = service.getUri();
				// ------------------------------------------------------------------
				// CREO LA TESTATA
				Map<String, String> headerParam = getHeadersInfo(request);				
				HttpHeaders headers = new HttpHeaders();				
				for (Entry<String, String> entry :headerParam.entrySet()) {
					headers.set(entry.getKey(),entry.getValue());
			    }
				// ------------------------------------------------------------------
				System.out.println(serviceURI.toString());
				// ------------------------------------------------------------------		
				
//				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/public/api/helloPath")
//				    .queryParam("name", "dario");
//
//				HttpEntity<?> entity = new HttpEntity<>(headers);
//				  HttpEntity<Object> response2 = restTemplate.exchange(
//						  servicePort,
//				  HttpMethod.GET,
//				  entity,
//				  HelloMessage.class);
				
				// ------------------------------------------------------------------
				break;
			}
		}
		return null;
	}
	
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
