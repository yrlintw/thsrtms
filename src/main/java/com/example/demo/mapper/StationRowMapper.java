package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Station;

public class StationRowMapper implements RowMapper<Station> {

	@Override
	public Station mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Station station = new Station();
		station.setStationId(rs.getInt("station_id"));
		station.setStationName(rs.getString("station_name"));
		
		return station;
	}

}
