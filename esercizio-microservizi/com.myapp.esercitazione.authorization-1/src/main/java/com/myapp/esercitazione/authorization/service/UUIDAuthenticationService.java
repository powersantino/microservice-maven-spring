package com.myapp.esercitazione.authorization.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.myapp.esercitazione.authorization.authentication.UserAuthenticationService;
import com.myapp.esercitazione.authorization.commons.Commons;
import com.myapp.esercitazione.authorization.model.UserLog;

@Service
public class UUIDAuthenticationService implements UserAuthenticationService {
    @Autowired
    private UserLogService userService;
    @Autowired
    private SettingsService settingsService;
    
    
    @Override
    public String login(String username, String password) {     	
	     Optional<UserLog> ret = userService.getByUsername(username);
	     if(ret.isPresent()) {
	    	 UserLog user = ret.get();
	    	 if(user.getPassword().equals(password)) {
	    		 user.setToken(UUID.randomUUID().toString());
	    		 user.setTokenbirth(new Date());
                 userService.save(user);
	    		 return user.getToken();
	    	 }
	     }
	     return "";
    }

    @Override
    public UserLog authenticateByToken(String token) {
    	Optional<UserLog> ret = userService.getByToken(token);
    	if(!ret.isPresent()) {
    		return null;
    	}else {
    		// Verifico che il token non sia scaduto
    		Date generateToken = ret.get().getTokenbirth();
    		long millisDiff = new Date().getTime() - generateToken.getTime();
    		int minutes = (int) (millisDiff / 60000 % 60);
    		String duration = settingsService.getByKey(Commons.KEY_TIME_OUT_SESSION).get().getValue();
    		if(Integer.parseInt(duration) < minutes ) {
    			logout(ret.get().getUsername());
    			return null;
    		}
    	}
        return ret.get();
    }

    @Override
    public void logout(String username) {
        userService.getByUsername(username)
                .ifPresent(u -> {
                    u.setToken(null);
                    u.setTokenbirth(null);
                    userService.save(u);
                });
    }
}
