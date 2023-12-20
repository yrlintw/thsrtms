package com.example.demo.dto;

import java.util.ArrayList;

import com.example.demo.model.TrainNum;

public class CreateTrainNumRequest {
	
	public ArrayList<TrainNum> trainNumList;
	
	public ArrayList<TrainNum> getTrainNumList() {
		return trainNumList;
	}

	public void setTrainNumList(ArrayList<TrainNum> trainNumList) {
		this.trainNumList = trainNumList;
	}
}
