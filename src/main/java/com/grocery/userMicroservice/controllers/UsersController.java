package com.grocery.userMicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.userMicroservice.dtos.Users;
import com.grocery.userMicroservice.repositories.UsersRepo;
import com.grocery.userMicroservice.servicesImpl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/users")
public class UsersController {

	private UsersRepo usersRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    UserDetailsServiceImpl userService;
    
    public UsersController(UsersRepo usersRepo,
    BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepo = usersRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
    
    @PostMapping("/sign-up")
    public void signUp(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepo.save(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) throws Exception {
    	String users = "failure";
		try {
			boolean isUser = userService.authenticateUser(user.getUserName(), user.getPassword());
			if (isUser) {
				users = "Success";
			}
		} catch (Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
	@RequestMapping(value = "/get-users", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() throws Exception {
		String users = null;
		try {
			
		} catch (Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
