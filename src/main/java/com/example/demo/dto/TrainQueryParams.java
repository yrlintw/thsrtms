package com.example.demo.dto;

import java.time.LocalDate;

public class TrainQueryParams {

	private LocalDate runDate;
	private Integer stationStartId;
	private Integer stationEndId;
	private Integer departureHour;
	private Integer departureMin;
	private Integer arrivalHour;
	private Integer arrivalMin;
	private Integer limit;
	private Integer offset;
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
	public Integer getStationEndId() {
		return stationEndId;
	}
	public void setStationEndId(Integer stationEndId) {
		this.stationEndId = stationEndId;
	}
	public Integer getDepartureHour() {
		return departureHour;
	}
	public void setDepartureHour(Integer departureHour) {
		this.departureHour = departureHour;
	}
	public Integer getDepartureMin() {
		return departureMin;
	}
	public void setDepartureMin(Integer departureMin) {
		this.departureMin = departureMin;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getArrivalHour() {
		return arrivalHour;
	}
	public void setArrivalHour(Integer arrivalHour) {
		this.arrivalHour = arrivalHour;
	}
	public Integer getArrivalMin() {
		return arrivalMin;
	}
	public void setArrivalMin(Integer arrivalMin) {
		this.arrivalMin = arrivalMin;
	}
}
