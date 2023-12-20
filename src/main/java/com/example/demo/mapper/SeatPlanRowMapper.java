package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.SeatPlan;

public class SeatPlanRowMapper implements RowMapper<SeatPlan> {

	@Override
	public SeatPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SeatPlan seatPlan = new SeatPlan();
		seatPlan.setSeatPlan(rs.getInt("seat_plan"));
		seatPlan.setCarNum(rs.getInt("car_num"));
		seatPlan.setSeatTypeId(rs.getInt("seat_type_id"));
		seatPlan.setSeatTypeName(rs.getString("seat_type_name"));
		
		return seatPlan;
	}

}
