package com.myapp.esercitazione.gatwayproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.myapp.esercitazione.gatwayproxy.filters.ErrorFilter;
import com.myapp.esercitazione.gatwayproxy.filters.PostFilter;
import com.myapp.esercitazione.gatwayproxy.filters.PreFilter;
import com.myapp.esercitazione.gatwayproxy.filters.RouteFilter;


@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}

}
