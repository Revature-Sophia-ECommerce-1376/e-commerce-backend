package com.revature.utilities;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.revature.models.User;
import com.revature.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class Utils {

	public String generatePasswordResetToken(User u) {
		return new JwtTokenManager().generatePasswordResetToken(u);
	}
	
	// parses Jwt and checks if token is expired or not
	public static boolean hasTokenExpired(String token) {
		Jws<Claims> jws;
		try {
		    jws = Jwts.parserBuilder()  // (1)
		    .setSigningKey(SecurityConstants.getTokenSecret())         // (2)
		    .build()                    // (3)
		    .parseClaimsJws(token); // (4)
		    Claims claims = jws.getBody();
		    Date tokenExpirationDate = claims.getExpiration();
		    Date todayDate = new Date();
		    // we can safely trust the JWT
		}  
		catch (JwtException ex) {       // (5)
		    
		    // we *cannot* use the JWT as intended by its creator
			return false;
		}
		return true;
	}
	
}
