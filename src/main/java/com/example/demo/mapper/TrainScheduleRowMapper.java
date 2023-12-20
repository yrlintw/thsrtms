package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.TrainSchedule;

public class TrainScheduleRowMapper implements RowMapper<TrainSchedule> {

	@Override
	public TrainSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TrainSchedule trainSchedule = new TrainSchedule();
		trainSchedule.setTrainNum(rs.getInt("train_num"));
		trainSchedule.setStationId(rs.getInt("station_id"));
		trainSchedule.setStationName(rs.getString("station_name"));
		trainSchedule.setTime(LocalTime.of(rs.getInt("hour"), rs.getInt("min")));

		return trainSchedule;
	}
	
}
