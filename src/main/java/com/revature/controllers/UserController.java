package com.revature.controllers;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.OperationStatusModel;
import com.revature.models.PasswordResetModel;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
	
	private final UserService userv;
	
	public UserController(UserService userv) {
		this.userv = userv;
	}
	
	@Authorized
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
	
		Optional<User> optionalUser = userv.findById(userId);
		
		return ResponseEntity.ok(optionalUser.get());
	}
	
	
	@Authorized
	@PutMapping
	public ResponseEntity<User> update(@RequestBody User user) {
		return ResponseEntity.ok(userv.save(user));
	}
	
	
	/**
	 * post http://localhost:8080/password-reset-request
	 * Accepts a PasswordResetModel in request body.
	 * Sends token and new password to UserService to set the new password
	 * 
	 * @param passwordResetModel
	 * @return Json response with result of the operation.
	 */
	@PostMapping(path="/password-reset-request",
    		consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
    	OperationStatusModel returnValue = new OperationStatusModel();

    	boolean operationResult = userv.resetPassword(
    			passwordResetModel.getToken(), passwordResetModel.getPassword()
    	);
    	
    	returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.toString());
    	returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
    	
    	if(operationResult) {
    		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.toString());
    	}
    	return returnValue;
    	
    }
}
