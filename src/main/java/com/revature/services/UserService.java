package com.revature.services;

import com.revature.models.PasswordResetToken;
import com.revature.models.User;
import com.revature.repositories.PasswordResetTokenRepository;
import com.revature.repositories.UserRepository;
import com.revature.utilities.JwtTokenManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private JwtTokenManager jwtTokenManager;

    
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
    
    /**
     * Finds a User by email and creates a jwt token with user.
     * Also saves the jwt token in the passwordresettable and sends an email with
     * a link to reset their password along with the jwt token. 
     * 
     * @param email
     * @return boolean
     */
    public boolean requestPasswordReset(String email) {
    	
    	boolean returnValue = false;
    	
    	Optional<User> user = userRepository.findByEmail(email);
    	
    	if(!user.isPresent()) {
    		return returnValue;
    	}
    	User userObj = user.get();
    	String token = new JwtTokenManager().generatePasswordResetToken(userObj);
    	
    	// Entity used to store jwt
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
    
    
    /**
     * Checks that token is valid and is found in passwordresettable database table.
     * Creates new password for user and encodes it using PBKDF2 algorithm.
     * Finally it deletes the jwt from the passwordresettable database table.
     * 
     * @param token
     * @param password
     * @return boolean
     */
    public boolean resetPassword(String token, String password) {
    	boolean returnValue = false;
    	
    	if(jwtTokenManager.hasTokenExpired(token)) {
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
			e.printStackTrace();
		}
    	byte[] hash = {};
		try {
			hash = factory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		// convert byte[] to string
    	String newPassword = new String();
    	for(int i = 0; i < hash.length; i++) {
    		newPassword += hash[i];
    	}
    	// end of encrypting password
    	
    	// look up User from User stored in token, set new password and update user table
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
