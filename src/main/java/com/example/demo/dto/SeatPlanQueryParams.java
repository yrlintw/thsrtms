package com.example.demo.dto;

import java.time.LocalDate;

public class SeatPlanQueryParams {

	private Integer trainNum;
	private LocalDate runDate;
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
}
