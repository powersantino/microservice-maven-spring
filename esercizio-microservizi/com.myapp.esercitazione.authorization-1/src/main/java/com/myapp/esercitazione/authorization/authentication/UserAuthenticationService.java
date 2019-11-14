package com.myapp.esercitazione.authorization.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Repository;

import com.myapp.esercitazione.authorization.model.UserLog;

@Repository
public interface UserAuthenticationService {
	String login(String username, String password) throws BadCredentialsException;	
    UserLog authenticateByToken(String token) throws AuthenticationException;    
    void logout(String username);
}
