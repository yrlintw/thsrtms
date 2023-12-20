package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainTimeTable {

	private Integer trainNum;
	private LocalDate runDate;
	private Integer stationStartId;
	private String stationStartName;
	private LocalTime startTime;
	private Integer stationEndId;
	private String stationEndName;
	private LocalTime endTime;
	private LocalTime travelTime;
	private String nonReservedCarNum;
	
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
	public Integer getStationStartId() {
		return stationStartId;
	}
	public void setStationStartId(Integer stationStartId) {
		this.stationStartId = stationStartId;
	}
	public String getStationStartName() {
		return stationStartName;
	}
	public void setStationStartName(String stationStartName) {
		this.stationStartName = stationStartName;
	}
	public Integer getStationEndId() {
		return stationEndId;
	}
	public void setStationEndId(Integer stationEndId) {
		this.stationEndId = stationEndId;
	}
	public String getStationEndName() {
		return stationEndName;
	}
	public void setStationEndName(String stationEndName) {
		this.stationEndName = stationEndName;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(LocalTime travelTime) {
		this.travelTime = travelTime;
	}
	public String getNonReservedCarNum() {
		return nonReservedCarNum;
	}
	public void setNonReservedCarNum(String nonReservedCarNum) {
		this.nonReservedCarNum = nonReservedCarNum;
	}
	
}
