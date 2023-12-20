package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

@Component
public class UserServiceImpl implements UserService {

	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {

		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
		if (user != null) {
			log.warn("{} 已被註冊", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		String hash = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
		userRegisterRequest.setPassword(hash);
		return userDao.register(userRegisterRequest);
	}
	
	@Override
	public User login(UserLoginRequest userLoginRequest) {
		User user = userDao.getUserByEmail(userLoginRequest.getEmail());
		if (user == null) {
			log.warn("{} 尚未註冊", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (user.getPassword().equals(DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes()))) {
			return user;
		} else {
			log.warn("{} 的密碼錯誤", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	@Transactional
	@Override
	public void updateUserById(Integer userId, UserRegisterRequest userRegisterRequest) {
		String hash = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
		userRegisterRequest.setPassword(hash);
		userDao.updateUserById(userId, userRegisterRequest);
	}

	@Override
	public void deleteUserById(Integer userId) {
		userDao.deleteUserById(userId);
	}
}