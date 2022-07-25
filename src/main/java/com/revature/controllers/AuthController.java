package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.Address;
import com.revature.models.OperationStatusModel;
import com.revature.models.PasswordResetRequestModel;
import com.revature.models.Purchase;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
@CrossOrigin(origins="*", allowedHeaders="*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        System.out.println(loginRequest.getEmail());
        System.out.println(loginRequest.getPassword());
        System.out.println(optional);
        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }
    
    @GetMapping("/password-reset-request")
    public String getPassword() {
    	return "Hellllllo";
    }
    
    /*
     * http://localhost:8080/auth/password-reset-request
     */
    @PostMapping(path="/password-reset-request", produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    		consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetRequest(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
    	
    	OperationStatusModel returnValue = new OperationStatusModel();
    	
    	boolean operationResult = authService.requestPasswordReset(passwordResetRequestModel.getEmail());
    	
    	returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
    	returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
    	
    	if(operationResult) {
    		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    	}
    	
    	return returnValue;
    	
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                "CUSTOMER",
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }
}
