package com.myapp.esercitazione.authorization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.myapp.esercitazione.authorization.commons.Commons;
import com.myapp.esercitazione.authorization.model.SettingModel;
import com.myapp.esercitazione.authorization.model.UserLog;
import com.myapp.esercitazione.authorization.repository.SettingsRepository;
import com.myapp.esercitazione.authorization.repository.UserLogRepository;
import com.myapp.esercitazione.authorization.service.SettingsService;

/*
 * Classe di debug per la gestione del DB.
 */

@Component
public class DataLoader implements ApplicationRunner {

    private UserLogRepository userRepository;
    private SettingsService settingsService;
    @Autowired
    public DataLoader(UserLogRepository userRepository,SettingsService settingsService ) {
        this.userRepository = userRepository;
        this.settingsService = settingsService;
    }
    @Override
    public void run(ApplicationArguments args) {
    	/*  
    	 * Un nuovo user può essere inserito con una lista di vuota di post o semplicemnte con un NULL.
    	 */
    	// User di prova (Admin)
    	UserLog user = new UserLog();
    	user.setPassword("password");
    	user.setUsername("username");    
    	user.setTokenbirth(null);
    	userRepository.save(user);    	
    	// Setting di prova, imposto la durata della sessione
    	SettingModel setting = new SettingModel();    	
    	setting.setKey(Commons.KEY_TIME_OUT_SESSION);
    	setting.setValue("1");    	
    	settingsService.save(setting);
    } 
}

	
	
