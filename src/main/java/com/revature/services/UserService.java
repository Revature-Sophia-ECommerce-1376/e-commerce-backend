package com.revature.services;

import com.revature.models.PasswordResetToken;
import com.revature.models.User;
import com.revature.repositories.PasswordResetTokenRepository;
import com.revature.repositories.UserRepository;
import com.revature.utilities.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
    
    
    
    public boolean resetPassword(String token, String password) {
    	boolean returnValue = false;
    	
    	if(Utils.hasTokenExpired(token)) {
    		return false;
    	}
    	
    	PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
    	
    	if(passwordResetToken == null) {
    		return false;
    	}
    	
    	// Prepare new Password using PBKDF2 since we're not using Spring Security
    	SecureRandom random = new SecureRandom();
    	byte[] salt = new byte[36];
    	random.nextBytes(salt);
    	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    	SecretKeyFactory factory = null;
    	
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	byte[] hash = {};
		try {
			hash = factory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String newPassword = new String();
    	for(int i = 0; i < hash.length; i++) {
    		newPassword += hash[i];
    	}
    	User user = passwordResetToken.getUserDetails();
    	user.setPassword(newPassword);
    	User savedUser = userRepository.save(user);
    	
    	if(savedUser != null && savedUser.getPassword().equalsIgnoreCase(newPassword)) {
    		returnValue = true;
    	}
    	
    	// Remove Password Reset token from the database
    	passwordResetTokenRepository.delete(passwordResetToken);
    	
    	return returnValue;
    }
    
}
