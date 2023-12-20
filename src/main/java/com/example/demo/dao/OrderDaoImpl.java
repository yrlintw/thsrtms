package com.example.demo.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.example.demo.dto.BuyItem;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.mapper.OrderItemRowMapper;
import com.example.demo.mapper.OrderRowMapper;
import com.example.demo.mapper.SeatRowMapper;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Seat;

@Component
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public ArrayList<Seat> getNorthboundSeats(CreateOrderRequest createOrderRequest) {
		String sql = "select seat_type_id, seat_num_id, sn.car_num, sn.row_num, sn.col_letter "
				+ "from (select seat_plan from train_nums "
				+ "where train_num_id = :trainNumId) tn "
				+ "left join seat_plans sm on tn.seat_plan = sm.seat_plan "
				+ "left join seat_nums sn on sm.car_num = sn.car_num "
				+ "where seat_type_id = :seatTypeId and not exists ( "
				+ "select seat_num_id "
				+ "from (select seat_num_id from order_items "
				+ "where station_start_id >= :stationStartId and station_end_id <= :stationStartId_ "
				+ "and train_num_id = :trainNumId and seat_type_id = :seatTypeId "
				+ "union all "
				+ "select seat_num_id from order_items "
				+ "where station_start_id <= :stationStartId_ and station_start_id >= :stationEndId_ "
				+ "and train_num_id = :trainNumId and seat_type_id = :seatTypeId) o "
				+ "where o.seat_num_id = sn.seat_num_id) ";
		
		//座位偏好靠窗
		if (createOrderRequest.getSeatPreference().equals("window")) {
			sql += "and (col_letter = 'A' or col_letter = 'E') ";
			
		//座位偏好靠走道
		} else if (createOrderRequest.getSeatPreference().equals("aisle")) {
			//標準車廂
			if (createOrderRequest.getSeatTypeId() == 1) {
				sql += "and (col_letter = 'B' or col_letter = 'C') ";
			
			//商務車廂
			} else if (createOrderRequest.getSeatTypeId() == 2) {
				sql += "and (col_letter = 'C' or col_letter = 'D') ";
			}
		}
		
		//訂票數量
		sql += "limit :limit ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNumId", createOrderRequest.getTrainNumId());
		map.put("seatTypeId", createOrderRequest.getSeatTypeId());
		map.put("stationStartId", createOrderRequest.getStationStartId());
		map.put("stationStartId_", createOrderRequest.getStationStartId() - 1);
		map.put("stationEndId_", createOrderRequest.getStationEndId() + 1);
		map.put("limit", createOrderRequest.getQuantity());
		
		List<Seat> list = namedParameterJdbcTemplate.query(sql, map, new SeatRowMapper());
		
		/*for (Seat seat: list) {
			System.out.println(seat.getSeatTypeId() + ", " 
					+ seat.getSeatNumId() + ", " 
					+ seat.getCarNum() + ", " 
					+ seat.getRowNum() + ", " 
					+ seat.getColLetter());
		}*/
		
		return (ArrayList<Seat>) list;
	}

	@Override
	public ArrayList<Seat> getSouthboundSeats(CreateOrderRequest createOrderRequest) {
		String sql = "select seat_type_id, seat_num_id, sn.car_num, sn.row_num, sn.col_letter "
				+ "from (select seat_plan from train_nums "
				+ "where train_num_id = :trainNumId) tn "
				+ "left join seat_plans sm on tn.seat_plan = sm.seat_plan "
				+ "left join seat_nums sn on sm.car_num = sn.car_num "
				+ "where seat_type_id = :seatTypeId and not exists ( "
				+ "select seat_num_id "
				+ "from (select seat_num_id from order_items "
				+ "where station_start_id <= :stationStartId and station_end_id >= :stationStartId_ "
				+ "and train_num_id = :trainNumId and seat_type_id = :seatTypeId "
				+ "union all "
				+ "select seat_num_id from order_items "
				+ "where station_start_id <= :stationEndId_ and station_start_id >= :stationStartId_ "
				+ "and train_num_id = :trainNumId and seat_type_id = :seatTypeId) o "
				+ "where o.seat_num_id = sn.seat_num_id)";
		
		//座位偏好靠窗
		if (createOrderRequest.getSeatPreference().equals("window")) {
			sql += "and (col_letter = 'A' or col_letter = 'E') ";
			
		//座位偏好靠走道
		} else if (createOrderRequest.getSeatPreference().equals("aisle")) {
			//標準車廂
			if (createOrderRequest.getSeatTypeId() == 1) {
				sql += "and (col_letter = 'B' or col_letter = 'C') ";
			
			//商務車廂
			} else if (createOrderRequest.getSeatTypeId() == 2) {
				sql += "and (col_letter = 'C' or col_letter = 'D') ";
			}
		}
		
		//訂票數量
		sql += "limit :limit ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNumId", createOrderRequest.getTrainNumId());
		map.put("seatTypeId", createOrderRequest.getSeatTypeId());
		map.put("stationStartId", createOrderRequest.getStationStartId());
		map.put("stationStartId_", createOrderRequest.getStationStartId() + 1);
		map.put("stationEndId_", createOrderRequest.getStationEndId() - 1);
		map.put("limit", createOrderRequest.getQuantity());
		
		List<Seat> list = namedParameterJdbcTemplate.query(sql, map, new SeatRowMapper());
		
		/*for (Seat seat: list) {
			System.out.println(seat.getSeatTypeId() + ", " 
					+ seat.getSeatNumId() + ", " 
					+ seat.getCarNum() + ", " 
					+ seat.getRowNum() + ", " 
					+ seat.getColLetter());
		}*/
		
		return (ArrayList<Seat>) list;
	}

	@Override
	public void createOrderItems(CreateOrderRequest createOrderRequest, ArrayList<Seat> seatList, Integer price) {
		String sql = "insert into order_items (order_id, train_num_id, station_start_id, station_end_id, seat_type_id, seat_num_id, "
				+ "ticket_type_id, amount) values(:orderId, :trainNumId, :stationStartId, :stationEndId, :seatTypeId, :seatNumId, "
				+ ":ticketTypeId, :amount)";
		
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[createOrderRequest.getQuantity()];
		for (int i = 0; i < createOrderRequest.getQuantity(); i++) {
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("orderId", createOrderRequest.getOrderId());
			parameterSources[i].addValue("trainNumId", createOrderRequest.getTrainNumId());
			parameterSources[i].addValue("stationStartId", createOrderRequest.getStationStartId());
			parameterSources[i].addValue("stationEndId", createOrderRequest.getStationEndId());
			parameterSources[i].addValue("seatTypeId", createOrderRequest.getSeatTypeId());
			parameterSources[i].addValue("seatNumId", seatList.get(i).getSeatNumId());
			parameterSources[i].addValue("ticketTypeId", createOrderRequest.getTicketTypeId());
			parameterSources[i].addValue("amount", price);
			
		}
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
	}

	@Override
	public Integer createOrder(Integer userId) {
		String sql = "insert into orders (user_id, total_amount, creation_date, last_modified_date) "
				+ "values (:userId, :totalAmount, :creationDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("totalAmount", 0);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		map.put("creationDate", LocalDateTime.now().format(dtf));
		map.put("lastModifiedDate", LocalDateTime.now().format(dtf));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		Integer orderId = keyHolder.getKey().intValue();
		
		return orderId;
	}

	@Override
	public void updateOrderTotalAmountByOrderId(Integer orderId, Integer totalAmount) {
		String sql = "update orders set total_amount = :totalAmount, "
				+ "last_modified_date = :lastModifiedDate where order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("totalAmount", totalAmount);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		map.put("lastModifiedDate", LocalDateTime.now().format(dtf));
		map.put("orderId", orderId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public Order getOrderById(Integer orderId) {
		String sql = "select order_id, user_id, total_amount, creation_date, "
				+ "last_modified_date from orders where order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		
		List<Order> list = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<OrderItem> getOrderItemsByOrderId(Integer orderId) {
		String sql = "select order_item_id, oi.order_id, "
				+ "oi.train_num_id, run_date, tn.train_num, "
				+ "station_start_id, s1.station_name as start_station_name, "
				+ "ts1.hour as start_hour, ts1.min as start_min, "
				+ "station_end_id, s2.station_name as end_station_name, "
				+ "ts2.hour as end_hour, ts2.min as end_min, "
				+ "oi.seat_type_id, seat_type_name, "
				+ "oi.seat_num_id, car_num, row_num, col_letter, "
				+ "oi.ticket_type_id, ticket_type_name, oi.amount "
				+ "from (select order_item_id, o2.order_id, train_num_id, "
				+ "station_start_id, station_end_id, seat_type_id, "
				+ "seat_num_id, ticket_type_id, amount "
				+ "from orders o1 left join order_items o2 on o1.order_id = o2.order_id "
				+ "where o1.order_id = :orderId) oi "
				+ "left join train_nums tn on oi.train_num_id = tn.train_num_id "
				+ "left join stations s1 on oi.station_start_id = s1.station_id "
				+ "left join train_schedules ts1 on ts1.train_num = tn.train_num "
				+ "and ts1.station_id = oi.station_start_id "
				+ "left join stations s2 on oi.station_end_id = s2.station_id "
				+ "left join train_schedules ts2 on ts2.train_num = tn.train_num "
				+ "and ts2.station_id = oi.station_end_id "
				+ "left join seat_types st on st.seat_type_id = oi.seat_type_id "
				+ "left join seat_nums sn on sn.seat_num_id = oi.seat_num_id "
				+ "left join ticket_types tt on tt.ticket_type_id = oi.ticket_type_id ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		
		List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
		
		return (ArrayList<OrderItem>) orderItemList;
	}

	@Override
	public void deleteOrder(Integer userId, Integer orderId) {
		String sql = "delete from orders where user_id = :userId "
				+ "and order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("orderId", orderId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void deleteOrderItemsByOrderId(Integer orderId) {
		String sql = "delete from order_items where order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public ArrayList<Order> getOrders(OrderQueryParams orderQueryParams) {
		String sql = "select order_id, user_id, total_amount, creation_date, "
				+ "last_modified_date from orders where user_id = :userId "
				+ "limit :limit offset :offset";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", orderQueryParams.getUserId());
		map.put("limit", orderQueryParams.getLimit());
		map.put("offset", orderQueryParams.getOffset());
		
		List<Order> list = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

		return (ArrayList<Order>) list;
	}

	@Override
	public Integer countOrders(OrderQueryParams orderQueryParams) {
		String sql = "select count(order_id) from orders where user_id = :userId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", orderQueryParams.getUserId());
		
		//queryForObject沒查到東西，資料庫會報錯，改用queryForList
		List<Integer> total = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		
		if (total.size() > 0) {
			return total.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateOrderItems(ArrayList<OrderItem> orderItemList, BuyItem buyItem, ArrayList<Seat> seatList, Integer price) {
		String sql = "update order_items set train_num_id = :trainNumId, seat_type_id = :seatTypeId, "
				+ "seat_num_id = :seatNumId, ticket_type_id = :ticketTypeId, amount = :amount "
				+ "where order_item_id = :orderItemId";
		
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[buyItem.getQuantity()];
		for (int i = 0; i < buyItem.getQuantity(); i++) {
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("trainNumId", buyItem.getTrainNumId());
			parameterSources[i].addValue("seatTypeId", buyItem.getSeatTypeId());
			parameterSources[i].addValue("seatNumId", seatList.get(i).getSeatNumId());
			parameterSources[i].addValue("ticketTypeId", buyItem.getTicketTypeId());
			parameterSources[i].addValue("amount", price);
			parameterSources[i].addValue("orderItemId", orderItemList.get(i).getOrderItemId());
		}
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
		
	}

	@Override
	public Integer getOrderTotalAmountByOrderId(Integer orderId) {
		String sql = "select total_amount from orders where order_id = :orderId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		
		List<Integer> totalAmount = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);

		if (totalAmount.size() > 0) {
			return totalAmount.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void deleteOrderItemByOrderItemId(Integer orderId, Integer orderItemId) {
		String sql = "delete from order_items where order_id = :orderId "
				+ "and order_item_id = :orderItemId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("orderItemId", orderItemId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public Integer getOrderItemAmountByOrderItemId(Integer orderItemId) {
		String sql = "select amount from order_items where order_item_id = :orderItemId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderItemId", orderItemId);
		
		List<Integer> amount = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		
		if (amount.size() > 0) {
			return amount.get(0);
		} else {
			return null;
		}
	}
}