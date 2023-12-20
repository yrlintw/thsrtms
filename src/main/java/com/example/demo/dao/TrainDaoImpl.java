package com.example.demo.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateTrainNumRequest;
import com.example.demo.dto.CreateTrainScheduleRequest;
import com.example.demo.dto.TrainNumQueryParams;
import com.example.demo.dto.TrainQueryParams;
import com.example.demo.mapper.TrainNumRowMapper;
import com.example.demo.mapper.TrainScheduleRowMapper;
import com.example.demo.mapper.TrainTimeTableRowMapper;
import com.example.demo.model.TrainNum;
import com.example.demo.model.TrainSchedule;
import com.example.demo.model.TrainTimeTable;

@Component
public class TrainDaoImpl implements TrainDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public TrainNum getTrainNumById(Integer trainNumId) {
		String sql = "select train_num, run_date, seat_plan from train_nums "
				+ "where train_num_id = :trainNumId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNumId", trainNumId);
		
		List<TrainNum> trainNum = namedParameterJdbcTemplate.query(sql, map, new TrainNumRowMapper());
		
		if (trainNum.size() > 0) {
			return trainNum.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void register(ArrayList<TrainNum> list) {
		String sql = "insert into train_nums (train_num, run_date, seat_plan) "
				+ "values(:trainNum, :runDate, :seatPlan)";
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[list.size()];
		for (int i = 0; i < list.size(); i++) {
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("trainNum", list.get(i).getTrainNum());
			parameterSources[i].addValue("runDate", list.get(i).getRunDate().format(dtf));
			parameterSources[i].addValue("seatPlan", list.get(i).getSeatPlan());
			
		}
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
		
	}

	@Override
	public ArrayList<TrainTimeTable> getTrainTimeTable(TrainQueryParams trainQueryParams) {
		String sql = "select tn.train_num, run_date, ts1.station_id as station_start_id, "
				+ "s1.station_name as station_start_name, ts1.hour as start_hour, ts1.min as start_min, "
				+ "ts2.station_id as station_end_id , s2.station_name as station_end_name, ts2.hour as end_hour, ts2.min as end_min "
				+ "from (select train_num, run_date from train_nums where run_date = :runDate) tn "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationStartId) ts1 "
				+ "on tn.train_num = ts1.train_num "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationEndId) ts2 "
				+ "on ts1.train_num = ts2.train_num "
				+ "inner join stations s1 "
				+ "on ts1.station_id = s1.station_id "
				+ "inner join stations s2 "
				+ "on ts2.station_id = s2.station_id "
				+ "where (ts1.hour > :departureHour or (ts1.hour = :departureHour and ts1.min >= :departureMin)) "
				+ "and ts2.train_schedule_id > ts1.train_schedule_id "
				+ "order by ts1.hour, ts1.min limit :limit offset :offset";
		
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", trainQueryParams.getRunDate().format(dtf));
		map.put("stationStartId", trainQueryParams.getStationStartId());
		map.put("stationEndId", trainQueryParams.getStationEndId());
		map.put("departureHour", trainQueryParams.getDepartureHour());
		map.put("departureMin", trainQueryParams.getDepartureMin());
		map.put("limit", trainQueryParams.getLimit());
		map.put("offset", trainQueryParams.getOffset());
		
		List<TrainTimeTable> list = namedParameterJdbcTemplate.query(sql, map, new TrainTimeTableRowMapper());
		
		return (ArrayList<TrainTimeTable>) list;
	}

	@Override
	public Integer countTrainTimeTable(TrainQueryParams trainQueryParams) {
		String sql = "select count(tn.train_num) "
				+ "from (select train_num, run_date from train_nums where run_date = :runDate) tn "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationStartId) ts1 "
				+ "on tn.train_num = ts1.train_num "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationEndId) ts2 "
				+ "on ts1.train_num = ts2.train_num "
				+ "inner join stations s1 "
				+ "on ts1.station_id = s1.station_id "
				+ "inner join stations s2 "
				+ "on ts2.station_id = s2.station_id "
				+ "where (ts1.hour > :departureHour or (ts1.hour = :departureHour and ts1.min >= :departureMin)) "
				+ "and ts2.train_schedule_id > ts1.train_schedule_id ";
		
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", trainQueryParams.getRunDate().format(dtf));
		map.put("stationStartId", trainQueryParams.getStationStartId());
		map.put("stationEndId", trainQueryParams.getStationEndId());
		map.put("departureHour", trainQueryParams.getDepartureHour());
		map.put("departureMin", trainQueryParams.getDepartureMin());
		
		List<Integer> total = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		
		if (total.size() > 0) {
			return total.get(0);
		} else {
			return 0;
		}
	}

	@Override
	public void createTrainSchedule(CreateTrainScheduleRequest createTrainScheduleRequest) {
		String sql = "insert into train_schedules (train_num, station_id, hour, min) "
				+ "values(:trainNum, :stationId, :hour, :min)";
		
		ArrayList<TrainSchedule> list = createTrainScheduleRequest.getTrainScheduleList();
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[list.size()];
		for (int i = 0; i < list.size(); i++) {
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("trainNum", list.get(i).getTrainNum());
			parameterSources[i].addValue("stationId", list.get(i).getStationId());
			parameterSources[i].addValue("hour", list.get(i).getTime().getHour());
			parameterSources[i].addValue("min", list.get(i).getTime().getMinute());
			
		}
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);

	}

	@Override
	public ArrayList<TrainSchedule> getTrainScheduleByTrainNum(Integer trainNum, LocalDate runDate) {
		String sql = "select tn.train_num, s.station_id, station_name, hour, min from train_nums tn "
				+ "left join train_schedules ts on tn.train_num = ts.train_num "
				+ "left join stations s on ts.station_id = s.station_id "
				+ "where tn.train_num = :trainNum ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNum", trainNum);
		
		if (runDate != null) {
			sql += "and tn.run_date = :runDate order by train_schedule_id ";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			map.put("runDate", runDate.format(dtf));
		
		} else {
			sql += "order by train_schedule_id ";
		}

		List<TrainSchedule> list = namedParameterJdbcTemplate.query(sql, map, new TrainScheduleRowMapper());
		
		return (ArrayList<TrainSchedule>) list;
	}

	@Override
	public ArrayList<TrainNum> getTrainNums(TrainNumQueryParams trainNumQueryParams) {
		String sql = "select train_num, run_date, seat_plan from train_nums where 1 =1 ";

		Map<String, Object> map = new HashMap<>();
		if (trainNumQueryParams.getTrainNum() != null) {
			sql += "and train_num = :trainNum ";
			map.put("trainNum", trainNumQueryParams.getTrainNum());
			
		}
		
		if (trainNumQueryParams.getRunDate() != null) {
			sql += "and run_date = :runDate";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			map.put("runDate", trainNumQueryParams.getRunDate().format(dtf));
		}
		
		List<TrainNum> list = namedParameterJdbcTemplate.query(sql, map, new TrainNumRowMapper());
		
		return (ArrayList<TrainNum>) list;
	}

	@Override
	public void deleteTrainNumById(Integer trainNumId) {
		String sql = "delete from train_nums where train_num_id = :trainNumId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNumId", trainNumId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void updateTrainNumSeatPlan(CreateTrainNumRequest createTrainNumRequest) {
		String sql = "update train_nums set seat_plan = :seatPlan "
				+ "where train_num = :trainNum and run_date = :runDate";
		
		ArrayList<TrainNum> list = createTrainNumRequest.getTrainNumList();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[list.size()];
		for (int i = 0; i < list.size(); i++) {
			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("trainNum", list.get(i).getTrainNum());
			parameterSources[i].addValue("runDate", list.get(i).getRunDate().format(dtf));
			parameterSources[i].addValue("seatPlan", list.get(i).getSeatPlan());
			
		}
		namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
		
	}

	@Override
	public void updateTrainScheduleTime(TrainSchedule trainSchedule) {
		String sql = "update train_schedules set hour = :hour, min = :min "
				+ "where train_num = :trainNum and station_id = :stationId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("hour", trainSchedule.getTime().getHour());
		map.put("min", trainSchedule.getTime().getMinute());
		map.put("trainNum", trainSchedule.getTrainNum());
		map.put("stationId", trainSchedule.getStationId());
		
		namedParameterJdbcTemplate.update(sql, map);
		
	}

	@Override
	public void deleteTrainScheduleByTrainNum(Integer trainNum) {
		String sql = "delete from train_schedules where train_num = :trainNum";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNum", trainNum);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public ArrayList<TrainTimeTable> getArrivalTrainTimeTable(TrainQueryParams trainQueryParams) {
		String sql = "select tn.train_num, run_date, ts1.station_id as station_start_id, "
				+ "s1.station_name as station_start_name, ts1.hour as start_hour, ts1.min as start_min, "
				+ "ts2.station_id as station_end_id , s2.station_name as station_end_name, ts2.hour as end_hour, ts2.min as end_min "
				+ "from (select train_num, run_date from train_nums where run_date = :runDate) tn "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationStartId) ts1 "
				+ "on tn.train_num = ts1.train_num "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationEndId) ts2 "
				+ "on ts1.train_num = ts2.train_num "
				+ "inner join stations s1 "
				+ "on ts1.station_id = s1.station_id "
				+ "inner join stations s2 "
				+ "on ts2.station_id = s2.station_id "
				+ "where (ts2.hour < :arrivalHour or (ts2.hour = :arrivalHour and ts2.min <= :arrivalMin)) "
				+ "and ts2.train_schedule_id > ts1.train_schedule_id "
				+ "order by ts2.hour desc, ts2.min desc limit :limit offset :offset";
		
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", trainQueryParams.getRunDate().format(dtf));
		map.put("stationStartId", trainQueryParams.getStationStartId());
		map.put("stationEndId", trainQueryParams.getStationEndId());
		map.put("arrivalHour", trainQueryParams.getArrivalHour());
		map.put("arrivalMin", trainQueryParams.getArrivalMin());
		map.put("limit", trainQueryParams.getLimit());
		map.put("offset", trainQueryParams.getOffset());
		
		List<TrainTimeTable> list = namedParameterJdbcTemplate.query(sql, map, new TrainTimeTableRowMapper());
		
		return (ArrayList<TrainTimeTable>) list;
	}

	@Override
	public Integer countArrivalTrainTimeTable(TrainQueryParams trainQueryParams) {
		String sql = "select count(tn.train_num) "
				+ "from (select train_num, run_date from train_nums where run_date = :runDate) tn "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationStartId) ts1 "
				+ "on tn.train_num = ts1.train_num "
				+ "inner join (select train_schedule_id, train_num, station_id, hour, min from train_schedules where station_id = :stationEndId) ts2 "
				+ "on ts1.train_num = ts2.train_num "
				+ "inner join stations s1 "
				+ "on ts1.station_id = s1.station_id "
				+ "inner join stations s2 "
				+ "on ts2.station_id = s2.station_id "
				+ "where (ts2.hour < :arrivalHour or (ts2.hour = :arrivalHour and ts2.min <= :arrivalMin)) "
				+ "and ts2.train_schedule_id > ts1.train_schedule_id ";
		
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", trainQueryParams.getRunDate().format(dtf));
		map.put("stationStartId", trainQueryParams.getStationStartId());
		map.put("stationEndId", trainQueryParams.getStationEndId());
		map.put("arrivalHour", trainQueryParams.getArrivalHour());
		map.put("arrivalMin", trainQueryParams.getArrivalMin());
		
		List<Integer> total = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		
		if (total.size() > 0) {
			return total.get(0);
		} else {
			return 0;
		}
	}

	@Override
	public Integer getTrainNumId(Integer trainNum, LocalDate runDate) {
		String sql = "select train_num_id from train_nums "
				+ "where train_num = :trainNum and run_date = :runDate";
		
		Map<String, Object> map = new HashMap<>();
		map.put("trainNum", trainNum);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		map.put("runDate", runDate.format(dtf));
		
		List<Integer> trainNumId = namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		if (trainNumId.size() > 0) {
			return trainNumId.get(0);
		} else {
			return null;
		}
	}
}
