package com.revature.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revature.SpringApplicationContext;

@Component
public class SecurityConstants {


	
	public static final long EXPIRATION_TIME = 864000000;	// 10 days
	public static final long PASSWORD_RESET_EXPIRATION_TIME = (1000*60*10);	// 10 min
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String PASSWORD_RESET_URL = "/password-reset-request";
	

	
	public static String getTokenSecret() {
		AppProperties appProperties =  (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}
	
}
