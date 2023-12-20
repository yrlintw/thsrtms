package com.example.demo.service;

import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

public interface UserService {
	
	public Integer register(UserRegisterRequest userRegisterRequest);
	
	public User getUserById(Integer userId);
	
	public void updateUserById(Integer userId, UserRegisterRequest userRegisterRequest);

	public void deleteUserById(Integer userId);
	
	public User login(UserLoginRequest userLoginRequest);

}
