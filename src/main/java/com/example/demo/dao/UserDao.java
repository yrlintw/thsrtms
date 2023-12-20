package com.example.demo.dao;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

public interface UserDao {
	
	public Integer register(UserRegisterRequest userRegisterRequest);

	public User getUserById(Integer userId);
	
	public User getUserByEmail(String email);

	public void updateUserById(Integer userId, UserRegisterRequest userRegisterRequest);

	public void deleteUserById(Integer userId);
	
}
