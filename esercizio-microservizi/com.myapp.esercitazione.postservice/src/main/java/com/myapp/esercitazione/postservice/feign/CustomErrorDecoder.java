package com.myapp.esercitazione.postservice.feign;
import feign.Response;
import feign.codec.ErrorDecoder;

// QUESTA CLASSE HA IL COMPITO DI INTERCETTARE LE ECCEZIONI GENERATE DAL FEIGN CLIENT.
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) { 
        switch (response.status()){
        	case 200:
        		return new Exception("Status 200, ok");
        	case 201:
        		return new Exception("Status 201, Created");
        	case 204:
        		return new Exception("Status 204, No Content");
        	case 300:
        		return new Exception("Status 300, Not Modified");        		
            case 400:
                return new Exception("Status 400, Bad request");
            case 401:
                return new Exception("Status 401, Unauthorized");
            case 403:
            	return new Exception("Status 403, Forbidden");
            case 404:
            	return new Exception("Status 404, Not found");
            case 410:
           	 	return new Exception("Status 410, Gone");
            case 500:            
           	 	return new Exception("Status 500, Internal server error");
            case 503:
            	return new Exception("Status 503, Service Unavailable");
            default:
            	return new Exception("Status 500, Internal server error");
        }
    }	
}
