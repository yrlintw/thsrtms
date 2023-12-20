package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.demo.dto.CreateTrainNumRequest;
import com.example.demo.dto.CreateTrainScheduleRequest;
import com.example.demo.dto.TrainNumQueryParams;
import com.example.demo.dto.TrainQueryParams;
import com.example.demo.model.TrainNum;
import com.example.demo.model.TrainSchedule;
import com.example.demo.model.TrainTimeTable;

public interface TrainService {

	public void register(CreateTrainNumRequest createTrainNumRequest);
	
	public ArrayList<TrainNum> getTrainNums(TrainNumQueryParams trainNumQueryParams);
	
	public void updateTrainNumSeatPlan(CreateTrainNumRequest createTrainNumRequest);

	public void deleteTrainNumById(Integer trainNumId);
	
	public void createTrainSchedule(CreateTrainScheduleRequest createTrainScheduleRequest);
	
	public ArrayList<TrainSchedule> getTrainScheduleByTrainNum(Integer trainNum, LocalDate runDate);
	
	public void updateTrainScheduleTime(TrainSchedule trainSchedule);
	
	public void deleteTrainScheduleByTrainNum(Integer trainNum);
	
	public ArrayList<TrainTimeTable> getTrainTimeTable(TrainQueryParams trainQueryParams);

	public Integer countTrainTimeTable(TrainQueryParams trainQueryParams);
	
	public ArrayList<TrainTimeTable> getArrivalTrainTimeTable(TrainQueryParams trainQueryParams);

	public Integer countArrivalTrainTimeTable(TrainQueryParams trainQueryParams);
	
}