package com.revature.controllers.user;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.UserRequest;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/private/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PrivateUserController {

    private final UserService userv;
	
	public PrivateUserController(UserService userv) {
		this.userv = userv;
	}
    @PutMapping
	public ResponseEntity<User> update(@RequestBody UserRequest user, HttpSession session) {
		
		User curUser = (User) session.getAttribute("user");
		System.out.println(curUser);
		curUser.setEmail(user.getEmail());
		curUser.setPassword(user.getPassword());
		curUser.setFirstName(user.getFirstName());
		curUser.setLastName(user.getLastName());
//		curUser.setAddresses(user.getAddresses());
		System.out.println(curUser);

		return ResponseEntity.ok(userv.save(curUser));
	}

	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody UserRequest user) {
		User newUser = new User(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRole());
		return ResponseEntity.ok(userv.save(newUser));
	}
}
