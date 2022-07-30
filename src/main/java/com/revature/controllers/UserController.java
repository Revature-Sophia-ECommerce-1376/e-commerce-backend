package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
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
import com.revature.dtos.UserRequest;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@Slf4j
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
	
	private final UserService userv;
	
	public UserController(UserService userv) {
		this.userv = userv;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
	
		Optional<User> optionalUser = userv.findById(userId);
		
		return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Authorized
	@GetMapping("/email/{userEmail}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("userEmail") String userEmail) {
		log.info(userEmail);
		Optional<User> optionalUser = userv.findByEmail(userEmail);
		return ResponseEntity.ok(optionalUser.get());
	}

	@Authorized
	@PutMapping
	public ResponseEntity<User> update(@RequestBody UserRequest user, HttpSession session) {
		
		User curUser = (User) session.getAttribute("user");
		log.info(curUser.toString());
		curUser.setEmail(user.getEmail());
		curUser.setPassword(user.getPassword());
		curUser.setFirstName(user.getFirstName());
		curUser.setLastName(user.getLastName());
		log.info(curUser.toString());
		return ResponseEntity.ok(userv.save(curUser));
	}

	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody UserRequest user) {
		User newUser = new User(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRole());
		return ResponseEntity.ok(userv.save(newUser));
	}
}
