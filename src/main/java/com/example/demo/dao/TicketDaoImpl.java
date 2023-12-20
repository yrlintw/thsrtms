package com.example.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.CreateTicketPriceRequest;
import com.example.demo.dto.CreateTicketTypeRequest;
import com.example.demo.dto.TicketQueryParams;
import com.example.demo.mapper.TicketRowMapper;
import com.example.demo.mapper.TicketTypeRowMapper;
import com.example.demo.model.Ticket;
import com.example.demo.model.TicketType;

@Component
public class TicketDaoImpl implements TicketDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer getTicketPrice(CreateOrderRequest createOrderRequest) {
		String sql = "select price from ticket_price tp "
				+ "left join ticket_types tt on tp.ticket_type_id = tt.ticket_type_id "
				+ "left join seat_types st on tp.seat_type_id = st.seat_type_id "
				+ "left join stations s1 on tp.station_start_id = s1.station_id "
				+ "left join stations s2 on tp.station_end_id = s2.station_id "
				+ "where station_start_id = :stationStartId and station_end_id = :stationEndId "
				+ "and tp.ticket_type_id = :tickectTypeId and tp.seat_type_id = :seatTypeId";
		
		Map<String, Object> map = new HashMap<>();
		if (createOrderRequest.getStationStartId() < createOrderRequest.getStationEndId()) {
			map.put("stationStartId", createOrderRequest.getStationStartId());
			map.put("stationEndId", createOrderRequest.getStationEndId());
			
		} else {
			map.put("stationStartId", createOrderRequest.getStationEndId());
			map.put("stationEndId", createOrderRequest.getStationStartId());
		}
		map.put("tickectTypeId", createOrderRequest.getTicketTypeId());
		map.put("seatTypeId", createOrderRequest.getSeatTypeId());
		
		//queryForObject沒查到東西，資料庫會報錯，改用queryForList
		List<Integer> price = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		
		if (price.size() > 0) {
			return price.get(0);
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<Ticket> getTicketPrice(TicketQueryParams ticketQueryParams) {
		String sql = "select ticket_price_id, s1.station_id as station_start_id, s1.station_name as station_start_name, "
				+ "s2.station_id as station_end_id, s2.station_name as station_end_name, "
				+ "tp.ticket_type_id, ticket_type_name, st.seat_type_id, seat_type_name, price "
				+ "from ticket_price tp left join ticket_types tt on tp.ticket_type_id = tt.ticket_type_id "
				+ "left join seat_types st on tp.seat_type_id = st.seat_type_id "
				+ "left join stations s1 on tp.station_start_id = s1.station_id "
				+ "left join stations s2 on tp.station_end_id = s2.station_id "
				+ "where station_start_id = :stationStartId and station_end_id = :stationEndId ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("stationStartId", ticketQueryParams.getStationStartId());
		map.put("stationEndId", ticketQueryParams.getStationEndId());
		
		if (ticketQueryParams.getSeatTypeId() != null) {
			sql += "and tp.seat_type_id = :seatTypeId ";
			map.put("seatTypeId", ticketQueryParams.getSeatTypeId());
		}
		
		if (ticketQueryParams.getTicketTypeId() != null) {
			sql += "and tt.ticket_type_id = :ticketTypeId";
			map.put("ticketTypeId", ticketQueryParams.getTicketTypeId());
		}
		
		List<Ticket> list = namedParameterJdbcTemplate.query(sql, map, new TicketRowMapper());
		
		return (ArrayList<Ticket>) list;
	}

	@Override
	public Integer create(CreateTicketPriceRequest createTicketPriceRequest) {
		String sql = "insert into ticket_price (ticket_type_id, seat_type_id, "
				+ "station_start_id, station_end_id, price) values(:ticketTypeId, "
				+ ":seatTypeId, :stationStartId, :stationEndId, :price)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("ticketTypeId", createTicketPriceRequest.getTicketTypeId());
		map.put("seatTypeId", createTicketPriceRequest.getSeatTypeId());
		map.put("stationStartId", createTicketPriceRequest.getStationStartId());
		map.put("stationEndId", createTicketPriceRequest.getStationEndId());
		map.put("price", createTicketPriceRequest.getPrice());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		Integer id = keyHolder.getKey().intValue();	
		return id;
	}

	@Override
	public void updateTicketPrice(CreateTicketPriceRequest createTicketPriceRequest) {
		String sql = "update ticket_price set price = :price where ticket_type_id = :ticketTypeId "
				+ "and seat_type_id = :seatTypeId and station_start_id = :stationStartId "
				+ "and station_end_id = :stationEndId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("ticketTypeId", createTicketPriceRequest.getTicketTypeId());
		map.put("seatTypeId", createTicketPriceRequest.getSeatTypeId());
		map.put("stationStartId", createTicketPriceRequest.getStationStartId());
		map.put("stationEndId", createTicketPriceRequest.getStationEndId());
		map.put("price", createTicketPriceRequest.getPrice());
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void daleteTicketPrice(Integer ticketPriceId) {
		String sql = "delete from ticket_price where ticket_price_id = :ticketPriceId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("ticketPriceId", ticketPriceId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public Integer createType(CreateTicketTypeRequest createTicketTypeRequest) {
		String sql = "insert into ticket_types (ticket_type_name) values (:ticketTypeName)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("ticketTypeName", createTicketTypeRequest.getTicketTypeName());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		Integer id = keyHolder.getKey().intValue();	
		return id;
	}

	@Override
	public ArrayList<TicketType> getTypes() {
		String sql = "select ticket_type_id, ticket_type_name from ticket_types "
				+ "order by ticket_type_id";
		
		Map<String, Object> map = new HashMap<>();
		
		List<TicketType> list = namedParameterJdbcTemplate.query(sql, map, new TicketTypeRowMapper());
		
		return (ArrayList<TicketType>) list;
	}

	@Override
	public void updateType(Integer ticketTypeId, CreateTicketTypeRequest createTicketTypeRequest) {
		String sql = "update ticket_types set ticket_type_name = :ticketTypeName "
				+ "where ticket_type_id = :ticketTypeId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("ticketTypeName", createTicketTypeRequest.getTicketTypeName());
		map.put("ticketTypeId", ticketTypeId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void deleteType(Integer ticketTypeId) {
		String sql = "delete from ticket_types where ticket_type_id = :ticketTypeId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("ticketTypeId", ticketTypeId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}
}
