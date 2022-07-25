package com.revature.services;

import com.revature.models.PasswordResetToken;
import com.revature.models.User;
import com.revature.repositories.PasswordResetTokenRepository;
import com.revature.repositories.UserRepository;
import com.revature.utilities.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    @Autowired
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    
    public UserService(UserRepository userRepository, 
    		PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    
    public Optional<User> findById(int id) {
    	return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public boolean requestPasswordReset(String email) {
    	
    	boolean returnValue = false;
    	
    	Optional<User> user = userRepository.findByEmail(email);
    	
    	if(!user.isPresent()) {
    		return returnValue;
    	}
    	User userObj = user.get();
    	String token = new Utils().generatePasswordResetToken(userObj);
    	
    	// Entity
    	PasswordResetToken passwordResetToken = new PasswordResetToken();
    	passwordResetToken.setToken(token);
    	passwordResetToken.setUserDetails(user.get());
    	passwordResetTokenRepository.save(passwordResetToken);

    	returnValue = new AmazonSES().sendPasswordResetRequest(
    			userObj.getFirstName(),
    			userObj.getEmail(),
    			token);
    	
    	return returnValue;
    }
}
