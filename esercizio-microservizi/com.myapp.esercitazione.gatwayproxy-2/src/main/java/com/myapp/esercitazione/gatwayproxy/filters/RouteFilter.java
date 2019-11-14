package com.myapp.esercitazione.gatwayproxy.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myapp.esercitazione.gatwayproxy.feign.AuthorizationFeignClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class RouteFilter extends ZuulFilter {
	
//  @Autowired
//  AuthorizationFeignClient authorizationFeignClient;
  
  @Override
  public String filterType() {
    return "route";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
//	   System.out.println("Inside Route Filter");
//	   RequestContext ctx = RequestContext.getCurrentContext();
//	   HttpServletRequest request = ctx.getRequest();	
//	   String token = request.getHeader("token");
//	   // Faccio la chiamata al boot di autorizzazione
//	   ResponseEntity<Object> responce = authorizationFeignClient.validateToken(token);
//	   if(HttpStatus.OK!= responce.getStatusCode() ) {
//		   // CHIAMATA NON AUTORIZZATA BLOCCO
//		   ctx.setSendZuulResponse(false);
//	       // response to client
//	       ctx.setResponseBody("API key not authorized in gateway");
//	       ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
//	       ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//	   }	
	   return null;
  }

}