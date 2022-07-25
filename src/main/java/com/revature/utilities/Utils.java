package com.revature.utilities;

import org.springframework.stereotype.Component;

import com.revature.models.User;

@Component
public class Utils {

	public String generatePasswordResetToken(User u) {
		return new JwtTokenManager().generatePasswordResetToken(u);
	}
	
}
