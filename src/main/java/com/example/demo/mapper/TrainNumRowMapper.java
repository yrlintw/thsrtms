package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.TrainNum;

public class TrainNumRowMapper implements RowMapper<TrainNum>{

	@Override
	public TrainNum mapRow(ResultSet rs, int rowNum) throws SQLException {
		TrainNum trainNum = new TrainNum();
		trainNum.setTrainNum(rs.getInt("train_num"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		trainNum.setRunDate(LocalDate.parse(rs.getString("run_date"), dtf));
		trainNum.setSeatPlan(rs.getInt("seat_plan"));

		return trainNum;
	}
}