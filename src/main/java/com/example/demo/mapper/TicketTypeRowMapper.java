package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.TicketType;

public class TicketTypeRowMapper implements RowMapper<TicketType> {

	@Override
	public TicketType mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TicketType ticketType = new TicketType();
		ticketType.setTicketTypeId(rs.getInt("ticket_type_id"));
		ticketType.setTicketTypeName(rs.getString("ticket_type_name"));
		
		return ticketType;
	}

}
