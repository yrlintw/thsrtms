package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.SeatDao;
import com.example.demo.dao.TrainDao;
import com.example.demo.dto.CreateTrainNumRequest;
import com.example.demo.dto.CreateTrainScheduleRequest;
import com.example.demo.dto.TrainNumQueryParams;
import com.example.demo.dto.TrainQueryParams;
import com.example.demo.model.TrainNum;
import com.example.demo.model.TrainSchedule;
import com.example.demo.model.TrainTimeTable;

@Component
public class TrainServiceImpl implements TrainService {

	private final static Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);
	
	@Autowired
	private TrainDao trainDao;
	
	@Autowired
	private SeatDao seatDao;
	
	@Transactional
	@Override
	public void register(CreateTrainNumRequest createTrainNumRequest) {
		
		ArrayList<TrainNum> list = createTrainNumRequest.getTrainNumList();
		TrainNumQueryParams trainNumQueryParams = new TrainNumQueryParams();
		
		for (TrainNum trainNum: list) {
			trainNumQueryParams.setTrainNum(trainNum.getTrainNum());
			trainNumQueryParams.setRunDate(trainNum.getRunDate());
			
			ArrayList<TrainNum> dbTrainNum = trainDao.getTrainNums(trainNumQueryParams);
			
			if (dbTrainNum.size() > 0) {
				log.warn("{} 運行的 {} 號列車日程已存在", dbTrainNum.get(0).getRunDate(), dbTrainNum.get(0).getTrainNum());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
		trainDao.register(list);
	}

	@Override
	public ArrayList<TrainTimeTable> getTrainTimeTable(TrainQueryParams trainQueryParams) {
		
		ArrayList<TrainTimeTable> list = trainDao.getTrainTimeTable(trainQueryParams);
		for (int i = 0; i < list.size(); i++) {
			List<Integer> carNums = seatDao.getNonReservedCarNums(list.get(i).getTrainNum(), list.get(i).getRunDate());
			list.get(i).setNonReservedCarNum(carNums.stream().map(o -> o.toString()).collect(Collectors.joining(", ")));
		}
		return list;
	}

	@Override
	public Integer countTrainTimeTable(TrainQueryParams trainQueryParams) {
		return trainDao.countTrainTimeTable(trainQueryParams);
	}

	@Override
	public void createTrainSchedule(CreateTrainScheduleRequest createTrainScheduleRequest) {
		
		Integer trainNum = createTrainScheduleRequest.getTrainScheduleList().get(0).getTrainNum();
		ArrayList<TrainSchedule> list = trainDao.getTrainScheduleByTrainNum(trainNum, null);
		
		if (list.size() > 0) {
			log.warn("{} 號列車排點已存在", trainNum);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		trainDao.createTrainSchedule(createTrainScheduleRequest);
	}

	@Override
	public ArrayList<TrainNum> getTrainNums(TrainNumQueryParams trainNumQueryParams) {
		return trainDao.getTrainNums(trainNumQueryParams);
	}

	@Override
	public void deleteTrainNumById(Integer trainNumId) {
		trainDao.deleteTrainNumById(trainNumId);
	}

	@Override
	public ArrayList<TrainSchedule> getTrainScheduleByTrainNum(Integer trainNum, LocalDate runDate) {
		ArrayList<TrainSchedule> list = trainDao.getTrainScheduleByTrainNum(trainNum, runDate);
		if (list.size() == 0) {
			log.warn("{} 號列車在 {} 非運行日", trainNum, runDate);
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return list;
	}

	@Override
	public void updateTrainNumSeatPlan(CreateTrainNumRequest createTrainNumRequest) {
		trainDao.updateTrainNumSeatPlan(createTrainNumRequest);
	}

	@Override
	public void updateTrainScheduleTime(TrainSchedule trainSchedule) {
		ArrayList<TrainSchedule> list = trainDao.getTrainScheduleByTrainNum(trainSchedule.getTrainNum(), null);
		
		if (list.size() == 0) {
			log.warn("{} 號列車不存在", trainSchedule.getTrainNum());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if (!containsObject(list, trainSchedule.getStationId())) {
			log.warn("車站代號 {} 不存在於 {} 號列車的排點", trainSchedule.getStationId(), trainSchedule.getTrainNum());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		trainDao.updateTrainScheduleTime(trainSchedule);
	}
	
	private static boolean containsObject(final List<TrainSchedule> list, final Integer stationId){
	    return list.stream().filter(o -> o.getStationId().equals(stationId)).findFirst().isPresent();
	}

	@Override
	public void deleteTrainScheduleByTrainNum(Integer trainNum) {
		trainDao.deleteTrainScheduleByTrainNum(trainNum);
	}

	@Override
	public ArrayList<TrainTimeTable> getArrivalTrainTimeTable(TrainQueryParams trainQueryParams) {
		ArrayList<TrainTimeTable> list = trainDao.getArrivalTrainTimeTable(trainQueryParams);
		for (int i = 0; i < list.size(); i++) {
			List<Integer> carNums = seatDao.getNonReservedCarNums(list.get(i).getTrainNum(), list.get(i).getRunDate());
			list.get(i).setNonReservedCarNum(carNums.stream().map(o -> o.toString()).collect(Collectors.joining(", ")));
		}
		return list;
	}

	@Override
	public Integer countArrivalTrainTimeTable(TrainQueryParams trainQueryParams) {
		return trainDao.countArrivalTrainTimeTable(trainQueryParams);
	}
	
}
