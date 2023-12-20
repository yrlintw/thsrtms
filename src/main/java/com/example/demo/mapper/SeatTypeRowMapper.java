package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.SeatType;

public class SeatTypeRowMapper implements RowMapper<SeatType> {

	@Override
	public SeatType mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SeatType seatType = new SeatType();
		seatType.setSeatTypeId(rs.getInt("seat_type_id"));
		seatType.setSeatTypeName(rs.getString("seat_type_name"));
		
		return seatType;
	}
}
