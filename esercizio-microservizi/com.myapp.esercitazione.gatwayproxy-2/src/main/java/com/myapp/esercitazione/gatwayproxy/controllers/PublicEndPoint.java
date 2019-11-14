package com.myapp.esercitazione.gatwayproxy.controllers;

import java.io.BufferedReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myapp.esercitazione.gatwayproxy.feign.AuthorizationFeignClient;
import com.myapp.esercitazione.gatwayproxy.feign.RegistryServiceFeignClient;




@Controller
public class PublicEndPoint {
	
	@Autowired
	RegistryServiceFeignClient registryServiceFeignClient;
	
    @Autowired
    AuthorizationFeignClient authorizationFeignClient;
    
	/***
	 * Endpoint che ha la funzione di intercettare le chiamate, vedere verso quale serivizio sono rivolte,
	 * identificare l'url del servizio e costruire la chiamata clonando la request arrivata.
	 * @param Application Name e la chiamta specifica per il servizio richiesto.
	 * @return ResponseEntity con all'interno il JSON di risposta.
	 */
 	@RequestMapping("/api/{applicationName}/**")
    public ResponseEntity<Object> serviceInstancesByApplicationName(@PathVariable String applicationName,@RequestParam("token") String token) { 		  
 		   // Ottengo l'URI della chiamata
 		   ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
 		   // Verifico che non si tratta di un login, la chiamata verso il login è gestita da zuul (solo a scopo didattico)
 		   // la chiamata del LOGIN potrebbe stare tranquillamente qui quindi inserisco una logica di verifica.  
 		   // N.B.: La stringa cosi login deve essere sostituita con un riferiento a commons, che sarà accessibile a tutti i boot, 
 		   // cosi se volessi sostituire l'indirizzo con /mylogin vado a cambiarlo in un solo punto.
 		   // Questo tipo di accesso dovrebbe essere evitato anche con la verifica del PARAMETRO token.
 		   if(!builder.build().toUri().toString().contains("/login")) {
 			   // NON SIAMO IN CASO DI LOGIN
 			   // Passo alla verifica del token 
 			   // QUI PARTE LA VERIFICA DEL TOKEN
 	 		   // se il token non è valorizzato la connessione non deve andare avanti. 
 	 		   // -------------------------------------------------------------------- 			 
			   System.out.println(token);		  
			   // Faccio la chiamata al boot di autorizzazione
			   if(token ==null && token.isEmpty()) {
				   // CHIAMATA NON AUTORIZZATA BLOCCO
				   return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);	  	
			   }else {				   
				   ResponseEntity<Object> responce = null;
				   try {
					   responce = authorizationFeignClient.validateToken(token);
					   // ------------------------------------------------------
					   if(responce!=null) {
						   // TOKEN PRESENTE DEL DB
							 if(HttpStatus.OK!= responce.getStatusCode() ) {
							   	 // CHIAMATA NON AUTORIZZATA BLOCCO
								 return responce;
							 }else {	
								 // CHIAMATA AUTORIZZATA
								 // Interrogo il service-registry e recupero l'inderizzo del servio dal registry. 		   
								 String serviceInstanceURI = registryServiceFeignClient.getInstanceURI(applicationName); 			   
								 // Ho il nuovo indirizzo ottenuto dal registry 		    	
								 String newURI = serviceInstanceURI + builder.build().toUri().toString().substring(builder.build().toUri().toString().indexOf(applicationName)).replace(applicationName, ""); 		  
								 try { 			   
								   		// URI AGGIORNATA
				 			   			URI newUri = new URI(newURI); 			   	
				 			   			// Recupero la chiamata appena arrivata per acquisire i parametri inviati.
				 			   			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				 			   			// CREO LA TESTATA
				 			   			HttpHeaders headers = buildHttpHeadersFromRequest(request);
				 			   			// Creo la chiamata
				 			   			RestTemplate restTemplate = new RestTemplate();
				 			   			// Inserisco il body e la testata
				 			   			HttpEntity<?> entity = new HttpEntity<>(getRequestBody(request),headers);
				 			   			// Faccio la chiamata				
				 			   			return restTemplate.exchange( newUri, getMetodFromRequest(request), entity, Object.class);
								 } catch (URISyntaxException e) {
									 return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);
								 }
							 }
						   }else {
							   // TOKEN NON PRESENTE SUL DB
							   return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);	 
						   }
					   // ------------------------------------------------------
				   }catch (Exception e) {
					   // CHIAMATA NON AUTORIZZATA BLOCCO
					   return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
				   }
			   }
 		   }
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }
 	
 	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement().toString();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
 	
 	private String getRequestBody(final HttpServletRequest request) {
 	    final StringBuilder builder = new StringBuilder();
 	    try (BufferedReader reader = request.getReader()) {
 	        if (reader == null) {
 	        	System.out.println("Il corpo è vuoto");
 	            return null;
 	        }
 	        String line;
 	        while ((line = reader.readLine()) != null) {
 	            builder.append(line);
 	        }
 	        return builder.toString();
 	    } catch (final Exception e) {
 	    	System.out.println("Eccezione nella lettura del corpo della chiamata, e: " + e);
 	        return null;
 	    }
 	}
 	
 	private HttpMethod getMetodFromRequest(HttpServletRequest request) {
 		HttpMethod method = HttpMethod.GET; 
		switch (request.getMethod()) {
		case "POST":
			method = HttpMethod.POST;
			break;
		case "DELETE":
			method = HttpMethod.DELETE;
			break;
		case "PUT":
			method = HttpMethod.PUT;
			break;
		default:
			break;
		} 
		return method;
 	}
 	
 	private HttpHeaders buildHttpHeadersFromRequest(HttpServletRequest request) {
 		Map<String, String> headerParam = getHeadersInfo(request);				
		HttpHeaders headers = new HttpHeaders();				
		for (Entry<String, String> entry :headerParam.entrySet()) {
			headers.set(entry.getKey(),entry.getValue());
	    }
		return headers;
 	}
}
