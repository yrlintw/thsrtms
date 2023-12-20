package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

	@Override
	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderItemId(rs.getInt("order_item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setTrainNumId(rs.getInt("train_num_id"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		orderItem.setRunDate(LocalDate.parse(rs.getString("run_date"), dtf));
		orderItem.setTrainNum(rs.getInt("train_num"));
		
		orderItem.setStationStartId(rs.getInt("station_start_id"));
		orderItem.setStationStartName(rs.getString("start_station_name"));
		LocalTime startTime = LocalTime.of(rs.getInt("start_hour"), rs.getInt("start_min"));
		orderItem.setStartTime(startTime);
		
		orderItem.setStationEndId(rs.getInt("station_end_id"));
		orderItem.setStationEndName(rs.getString("end_station_name"));
		LocalTime endTime = LocalTime.of(rs.getInt("end_hour"), rs.getInt("end_min"));
		orderItem.setEndTime(endTime);
		
		long hours = ChronoUnit.HOURS.between(startTime, endTime); 
		long minutes = ChronoUnit.MINUTES.between(startTime, endTime) % 60; 
		LocalTime travelTime = LocalTime.of(Math.toIntExact(hours), Math.toIntExact(minutes));
		orderItem.setTravelTime(travelTime);
		
		orderItem.setSeatTypeId(rs.getInt("seat_type_id"));
		orderItem.setSeatTypeName(rs.getString("seat_type_name"));
		orderItem.setSeatNumId(rs.getInt("seat_num_id"));
		orderItem.setCarNum(rs.getInt("car_num"));
		orderItem.setRowNum(rs.getInt("row_num"));
		orderItem.setColLetter(rs.getString("col_letter"));
		orderItem.setTicketTypeId(rs.getInt("ticket_type_id"));
		orderItem.setTicketTypeName(rs.getString("ticket_type_name"));
		orderItem.setAmount(rs.getInt("amount"));
		
		return orderItem;
	}
}
