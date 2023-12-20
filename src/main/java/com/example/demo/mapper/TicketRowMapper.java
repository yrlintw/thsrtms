package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Ticket;

public class TicketRowMapper implements RowMapper<Ticket> {

	@Override
	public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Ticket ticket = new Ticket();
		ticket.setTicketPriceId(rs.getInt("ticket_price_id"));
		ticket.setStationStartId(rs.getInt("station_start_id"));
		ticket.setStationStartName(rs.getString("station_start_name"));
		ticket.setStationEndId(rs.getInt("station_end_id"));
		ticket.setStationEndName(rs.getString("station_end_name"));
		ticket.setTicketTypeId(rs.getInt("ticket_type_id"));
		ticket.setTicketTypeName(rs.getString("ticket_type_name"));
		ticket.setSeatTypeId(rs.getInt("seat_type_id"));
		ticket.setSeatTypeName(rs.getString("seat_type_name"));
		ticket.setPrice(rs.getInt("price"));
		
		return ticket;
	}

}
