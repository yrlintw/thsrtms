package com.example.demo.dto;

import java.util.ArrayList;

import com.example.demo.model.TrainSchedule;

public class CreateTrainScheduleRequest {
	
	public ArrayList<TrainSchedule> trainScheduleList;

	public ArrayList<TrainSchedule> getTrainScheduleList() {
		return trainScheduleList;
	}

	public void setTrainScheduleList(ArrayList<TrainSchedule> trainScheduleList) {
		this.trainScheduleList = trainScheduleList;
	}

}
