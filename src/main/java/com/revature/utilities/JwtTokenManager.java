package com.revature.utilities;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.security.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component // we declare it as a Spring Bean (object managed by the Spring IoC container)
public class JwtTokenManager {
	
	private final Key key; // from java.security
	private final Logger logger = LoggerFactory.getLogger(JwtTokenManager.class);

	public JwtTokenManager(){
		// what is a key?
		// a set of public keys used to verify a token and have it be parsed by our server
		key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}

	// this builds the payload which is encrypted info about the user we're authenticating
	public String issueToken(User user){
		return Jwts.builder() // io.jsonwebtoken
				// payload 
				.setId(String.valueOf(user.getId()))
				.setSubject(user.getEmail())
				.setIssuer("e-commerce-backend") // the source that generated the token
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(key).compact();
	}
	
	// this builds the payload which is encrypted info about the user we're authenticating
		public String generatePasswordResetToken(User user){
			return Jwts.builder() // io.jsonwebtoken
					// payload 
					.setId(String.valueOf(user.getId()))
					.setSubject(user.getEmail())
					.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME))	// 10min
					.setIssuer("e-commerce-backend") // the source that generated the token
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.signWith(key).compact();
		}
	
	public int parseUserIdFromToken(String token){
		
		try {
			return Integer.parseInt(Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					// this is the way in which we can READ user data from a token
					.parseClaimsJws(token).getBody().getId());
			
		} catch (Exception e){
			logger.warn("JWT error parsing user id from token");
			throw new NotLoggedInException("Unable to parse user id from JWT. Please sign in again");
		}
	}

}
