package com.example.demo.dto;

import java.time.LocalDate;

public class TrainNumQueryParams {

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
	
}
