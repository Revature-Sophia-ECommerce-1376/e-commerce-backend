package com.revature.repositories;

import org.springframework.data.repository.CrudRepository;

import com.revature.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long>{
	
	PasswordResetToken findByToken(String token);
	
}
