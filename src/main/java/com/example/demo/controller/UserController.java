package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/users/register")
	public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
		
		Integer userId = userService.register(userRegisterRequest);
		User user = userService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PostMapping("/users/login")
	public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
		
		User user = userService.login(userLoginRequest);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<User> read(@PathVariable Integer userId) {
		
		User user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<User> update(@PathVariable Integer userId,
			@RequestBody UserRegisterRequest userRegisterRequest) {
		
		User user = userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		userService.updateUserById(userId, userRegisterRequest);
		user = userService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> delete(@PathVariable Integer userId) {
		
		userService.deleteUserById(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
