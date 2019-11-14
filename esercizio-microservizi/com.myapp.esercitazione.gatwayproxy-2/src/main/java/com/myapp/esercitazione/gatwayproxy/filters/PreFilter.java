package com.myapp.esercitazione.gatwayproxy.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myapp.esercitazione.gatwayproxy.feign.AuthorizationFeignClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {
	
  @Autowired
  AuthorizationFeignClient authorizationFeignClient;
	
  private String tokenArived = ""; 
  @Override
  public String filterType() {
	return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }
  /***
   * Nell'override del merodo shouldFilter() ho inserito la validazione
   * esclusa la chiamata che viene fatta per il login.
   */
  @Override
  public boolean shouldFilter() {		  
		  RequestContext ctx = RequestContext.getCurrentContext();	 
		  HttpServletRequest request = ctx.getRequest();	
		  // Condizione che evita la validazione sul login
		  if(!request.getRequestURL().toString().contains("/login")) {
			  String token = request.getParameter("token");
			  System.out.println(token);		  
			  // Faccio la chiamata al boot di autorizzazione
			  if(token !=null && !token.isEmpty()) {
			    	this.tokenArived = token;		  	
			  }else {
			    	// CHIAMATA NON AUTORIZZATA BLOCCO
			 	   	ctx.setSendZuulResponse(false);
			 	   	ctx.setResponseBody("API key not authorized in gateway");
			 	   	ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
			 	   	ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			  }  
			  ResponseEntity<Object> responce = null;
			  try {
				  responce = authorizationFeignClient.validateToken(this.tokenArived);
			  }catch (Exception e) {
				   // CHIAMATA NON AUTORIZZATA BLOCCO
			  	   ctx.setSendZuulResponse(false);
			       ctx.setResponseBody("API key not authorized in gateway");
			       ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
			       ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			  }
			  if(responce!=null) {
				 if(HttpStatus.OK!= responce.getStatusCode() ) {
				   	   // CHIAMATA NON AUTORIZZATA BLOCCO
				   	   ctx.setSendZuulResponse(false);
				       ctx.setResponseBody("API key not authorized in gateway");
				       ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
				       ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
				 }
			  }else {
			   	// CHIAMATA NON AUTORIZZATA BLOCCO
			  	ctx.setSendZuulResponse(false);
			   	ctx.setResponseBody("API key not authorized in gateway");
			   	ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
			   	ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			  }
		  } 
		  if ( ctx.getRequest().getRequestURI() == null || !ctx.getRequest().getRequestURI().startsWith("/url")) {
			  return false;  
		  }
		  return ctx.getRouteHost() != null && ctx.sendZuulResponse();
  }

  @Override
  public Object run() {	
//    RequestContext ctx = RequestContext.getCurrentContext();
//    HttpServletRequest request = ctx.getRequest();    
    return null;
  }

}