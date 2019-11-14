package com.myapp.esercitazione.registryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicEndPoint {
	@Autowired
    private DiscoveryClient discoveryClient;

	/***
	 * Endpoint che ha la funzione di cercare l'host in base all'id del servizio presente nella request che arriva al gateway
	 * @param Application Name indicato nel file property del servizio registrato
	 * @return String URI service
	 */
    @RequestMapping("/service-instances/{applicationName}")
    public String serviceInstancesURIByApplicationNameS(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName).get(0).getUri().toString();
    }
    @RequestMapping("/service-instances-port/{applicationName}")
    public int serviceInstancesPortByApplicationNameS(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName).get(0).getPort();
    }
}
