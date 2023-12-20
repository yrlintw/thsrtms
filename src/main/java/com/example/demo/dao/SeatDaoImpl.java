package com.example.demo.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.SeatPlanQueryParams;
import com.example.demo.mapper.SeatPlanRowMapper;
import com.example.demo.mapper.SeatTypeRowMapper;
import com.example.demo.model.SeatPlan;
import com.example.demo.model.SeatType;

@Component
public class SeatDaoImpl implements SeatDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public ArrayList<SeatPlan> getSeatPlan(SeatPlanQueryParams seatPlanQueryParams) {
		String sql = "select tn.seat_plan, car_num, sm.seat_type_id, seat_type_name "
				+ "from (select seat_plan from train_nums tn "
				+ "where run_date = :runDate and train_num = :trainNum) tn "
				+ "left join seat_plans sm "
				+ "on tn.seat_plan = sm.seat_plan "
				+ "left join seat_types st "
				+ "on sm.seat_type_id = st.seat_type_id";
		
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", seatPlanQueryParams.getRunDate().format(dtf));
		map.put("trainNum", seatPlanQueryParams.getTrainNum());
		
		List<SeatPlan> list = namedParameterJdbcTemplate.query(sql, map, new SeatPlanRowMapper());
		
		return (ArrayList<SeatPlan>) list;
	}

	@Override
	public ArrayList<Integer> getNonReservedCarNums(Integer trainNum, LocalDate runDate) {
		String sql = "select car_num from train_nums t left join seat_plans s on t.seat_plan = s.seat_plan "
				+ "where train_num = :trainNum and run_date = :runDate and seat_type_id = 3";
		
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", runDate.format(dtf));
		map.put("trainNum", trainNum);
		
		List<Integer> list = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		
		return (ArrayList<Integer>) list;
	}

	@Override
	public ArrayList<SeatType> getSeatTypes() {
		String sql = "select seat_type_id, seat_type_name from seat_types order by seat_type_id";
		
		Map<String, Object> map = new HashMap<>();
		
		List<SeatType> list = namedParameterJdbcTemplate.query(sql, map, new SeatTypeRowMapper());
		
		return (ArrayList<SeatType>) list;
	}
}
