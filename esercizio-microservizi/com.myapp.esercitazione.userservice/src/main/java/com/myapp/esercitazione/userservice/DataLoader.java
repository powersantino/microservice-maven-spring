package com.myapp.esercitazione.userservice;


import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.myapp.esercitazione.userservice.entity.UserEntity;
import com.myapp.esercitazione.userservice.repository.UserRepository;

/*
 * Classe di debug per la gestione del DB.
 */

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void run(ApplicationArguments args) {
    	/*  
    	 * Un nuovo user può essere inserito con una lista di vuota di post o semplicemnte con un NULL.
    	 */
    	userRepository.save(new UserEntity(2, "username", "mail@mail.com", "aaasdwefvd322563bhg"));    	
    }  
    
    /***  
	 *
     * 	DEBUG - PERSISTENZA DATABASE H2
     * 	Con questo metodo espondo il DB sulla porta 9090 così da renderlo accessibile 
     *  a altri spring boot.
     */
 	@Bean(initMethod = "start", destroyMethod = "stop")
     public Server inMemoryH2DatabaseServer() throws SQLException {
         return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
 	}
    
}

	
	
