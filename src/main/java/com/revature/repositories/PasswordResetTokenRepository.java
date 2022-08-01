package com.revature.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long>{
	
	PasswordResetToken findByToken(String token);
	
}
