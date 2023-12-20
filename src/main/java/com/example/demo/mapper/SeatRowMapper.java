package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Seat;

public class SeatRowMapper implements RowMapper<Seat>{

	@Override
	public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Seat seat = new Seat();
		seat.setSeatNumId(rs.getInt("seat_num_id"));
		seat.setSeatTypeId(rs.getInt("seat_type_id"));
		seat.setCarNum(rs.getInt("car_num"));
		seat.setRowNum(rs.getInt("row_num"));
		seat.setColLetter(rs.getString("col_letter"));

		return seat;
	}
}