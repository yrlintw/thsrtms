package com.example.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.StationRowMapper;
import com.example.demo.model.Station;

@Component
public class StationDaoImpl implements StationDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public ArrayList<Station> getStations() {
		String sql = "select station_id, station_name from stations "
				+ "order by station_id";
		
		Map<String, Object> map = new HashMap<>();
		
		List<Station> list = namedParameterJdbcTemplate.query(sql, map, new StationRowMapper());
		
		return (ArrayList<Station>) list;
	}

	@Override
	public Station getStationById(Integer stationId) {
		String sql = "select station_id, station_name from stations where station_id = :stationId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("stationId", stationId);
		
		List<Station> list = namedParameterJdbcTemplate.query(sql, map, new StationRowMapper());
		
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
