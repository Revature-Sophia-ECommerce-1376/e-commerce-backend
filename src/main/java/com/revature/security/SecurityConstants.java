package com.revature.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.SpringApplicationContext;
import com.revature.utilities.JwtTokenManager;

/**
 * Defines security settings used in the application. 
 * 
 * @author andrewhughes
 *
 */

@Component
public class SecurityConstants {

	@Autowired
	private JwtTokenManager jwtTokenManager;
	
	public static final long PASSWORD_RESET_EXPIRATION_TIME = (1000*60*10);	// 10 min
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String PASSWORD_RESET_URL = "/password-reset-request";
	

	/**
	 * Gets the JwtTokenManager bean stored in the ApplicationContext
	 * @return
	 */
	public static byte[] getTokenSecret() {
		JwtTokenManager jwtTokenManager =  (JwtTokenManager) SpringApplicationContext.getBean("JwtTokenManager");
		return jwtTokenManager.getKey();
	}
	
}
