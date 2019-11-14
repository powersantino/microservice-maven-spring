package com.myapp.esercitazione.userservice.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;

/*
 *  Classe di configurazione di Spring.  
 *  Qui devono essere indicate tutte le variazioni respetto la 
 *  defaoult.
 */

@Configuration
public class ClientConfiguration {
 
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}