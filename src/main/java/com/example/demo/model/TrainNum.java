package com.example.demo.model;

import java.time.LocalDate;

public class TrainNum {
	private Integer trainNumId;
	private Integer trainNum;
	private LocalDate runDate;
	private Integer seatPlan;
	public Integer getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(Integer trainNum) {
		this.trainNum = trainNum;
	}
	public LocalDate getRunDate() {
		return runDate;
	}
	public void setRunDate(LocalDate runDate) {
		this.runDate = runDate;
	}
	public Integer getSeatPlan() {
		return seatPlan;
	}
	public void setSeatPlan(Integer seatPlan) {
		this.seatPlan = seatPlan;
	}
	public Integer getTrainNumId() {
		return trainNumId;
	}
	public void setTrainNumId(Integer trainNumId) {
		this.trainNumId = trainNumId;
	}
}