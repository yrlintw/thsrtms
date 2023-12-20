package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.TrainTimeTable;

public class TrainTimeTableRowMapper implements RowMapper<TrainTimeTable> {

	@Override
	public TrainTimeTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TrainTimeTable trainTimeTable = new TrainTimeTable();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		trainTimeTable.setTrainNum(rs.getInt("train_num"));
		trainTimeTable.setRunDate(LocalDate.parse(rs.getString("run_date"), dtf));
		trainTimeTable.setStationStartId(rs.getInt("station_start_id"));
		trainTimeTable.setStationStartName(rs.getString("station_start_name"));
		trainTimeTable.setStationEndId(rs.getInt("station_end_id"));
		trainTimeTable.setStationEndName(rs.getString("station_end_name"));
		
		LocalTime startTime = LocalTime.of(rs.getInt("start_hour"), rs.getInt("start_min"));
		trainTimeTable.setStartTime(startTime);

		LocalTime endTime = LocalTime.of(rs.getInt("end_hour"), rs.getInt("end_min"));
		trainTimeTable.setEndTime(endTime);
		
		long hours = ChronoUnit.HOURS.between(startTime, endTime); 
		long minutes = ChronoUnit.MINUTES.between(startTime, endTime) % 60; 
		LocalTime travelTime = LocalTime.of(Math.toIntExact(hours), Math.toIntExact(minutes));
		trainTimeTable.setTravelTime(travelTime);
		
		return trainTimeTable;
	}
}
