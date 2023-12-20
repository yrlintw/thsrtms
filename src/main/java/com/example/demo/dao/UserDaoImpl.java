package com.example.demo.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.mapper.UserRowMapper;
import com.example.demo.model.User;

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		String sql = "insert into users (email, password, name, identification, "
				+ "passport_arc, phone, creation_date, last_modified_date) "
				+ "values(:email, :password, :name, :identification, "
				+ ":passportArc, :phone, :creationDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", userRegisterRequest.getEmail());
		map.put("password", userRegisterRequest.getPassword());
		map.put("name", userRegisterRequest.getName());
		map.put("identification", userRegisterRequest.getIdentification());
		map.put("passportArc", userRegisterRequest.getPassportArc());
		map.put("phone", userRegisterRequest.getPhone());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		map.put("creationDate", LocalDateTime.now().format(dtf));
		map.put("lastModifiedDate", LocalDateTime.now().format(dtf));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		Integer id = keyHolder.getKey().intValue();

		return id;
	}
	
	@Override
	public User getUserById(Integer userId) {
		String sql = "select user_id, email, password, name, identification, "
				+ "passport_arc, phone, creation_date, last_modified_date "
				+ "from users where user_id = :userId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		
		List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper()); 
		
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public User getUserByEmail(String email) {
		String sql = "select user_id, email, password, name, identification, "
				+ "passport_arc, phone, creation_date, last_modified_date "
				+ "from users where email = :email";
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		
		List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper()); 
		
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateUserById(Integer userId, UserRegisterRequest userRegisterRequest) {
		String sql = "update users set email = :email, password = :password, name = :name, "
				+ "identification = :identification, passport_arc = :passportArc, phone = :phone, "
				+ "last_modified_date = :lastModifiedDate where user_id = :userId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", userRegisterRequest.getEmail());
		map.put("password", userRegisterRequest.getPassword());
		map.put("name", userRegisterRequest.getName());
		map.put("identification", userRegisterRequest.getIdentification());
		map.put("passportArc", userRegisterRequest.getPassportArc());
		map.put("phone", userRegisterRequest.getPhone());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		map.put("lastModifiedDate", LocalDateTime.now().format(dtf));
		map.put("userId", userId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void deleteUserById(Integer userId) {
		String sql = "delete from users where user_id = :userId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

}
