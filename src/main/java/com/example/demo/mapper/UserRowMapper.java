package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setName(rs.getString("name"));
		user.setIdentification(rs.getString("identification"));
		user.setPassportArc(rs.getString("passport_arc"));
		user.setPhone(rs.getString("phone"));
		user.setCreationDate(rs.getString("creation_date"));
		user.setLastModifiedDate(rs.getString("last_modified_date"));
		return user;
	}
}