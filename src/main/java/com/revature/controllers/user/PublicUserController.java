package com.revature.controllers.user;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.revature.dtos.UserRequest;
import com.revature.models.User;

import com.revature.services.UserService;

@RestController
@RequestMapping("/api/public/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PublicUserController {
	
	private final UserService userv;
	
	public PublicUserController(UserService userv) {
		this.userv = userv;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
	
		Optional<User> optionalUser = userv.findById(userId);
		
		return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}


	@GetMapping("/email/{userEmail}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("userEmail") String userEmail) {
		System.out.println(userEmail);
		Optional<User> optionalUser = userv.findByEmail(userEmail);

		return ResponseEntity.ok(optionalUser.get());
	}

	
}
