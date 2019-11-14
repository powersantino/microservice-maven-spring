package com.myapp.esercitazione.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.esercitazione.authorization.authentication.UserAuthenticationService;
import com.myapp.esercitazione.authorization.model.UserLog;
@ComponentScan
@RestController
public class PublicEndpointsController {
    @Autowired
    private UserAuthenticationService authenticationService;
    /***
     * Endpoint per la validazione della username e della password
     * @param username
     * @param password
     * @return 
     * in caso di ok ritorna il token con HttpStatus 200, 
     * in caso di ko ritorna l'HttpStatus UNAUTHORIZED.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login( @RequestParam("username") String username, @RequestParam("password") String password) {       
    	if(!username.isEmpty() && !password.isEmpty()) {
    		String token = authenticationService.login(username, password);
    		if(!token.isEmpty()) {
    			return new ResponseEntity<>(token,HttpStatus.OK);
    		}else {
    			return new ResponseEntity<>("Credenziali errate!",HttpStatus.UNAUTHORIZED);    			
    		}
    	}else {
    		return new ResponseEntity<>("Dati mancanti",HttpStatus.BAD_REQUEST);
    	}
    }
    /***
     * Endpoint per la validazione del token 
     * @param token
     * @return in caso di ok 
     */
    @PostMapping("/token")
    public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {    	
    		UserLog userlog = authenticationService.authenticateByToken(token); 
    		if(userlog == null) {
    			// risp.: 401
    			return new ResponseEntity<>(new UserLog(), HttpStatus.NOT_FOUND);
    		}
    		return new ResponseEntity<>(userlog, HttpStatus.OK);  	
    }
    /***
     * Endpoint per eseguire il logout 
     * @param username
     * @return OK ritorna HttpStatus.ACCEPTED e in caso di KO HttpStatus.NOT_FOUND. 
     */
    @PostMapping("/logout")
    public ResponseEntity<Object> logOut(@RequestParam("username") String username) {
    	try {
    		authenticationService.logout(username);
    		return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
		} catch(BadCredentialsException e) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
    }
}
