package com.example.demo.model;

public class SeatPlan {
	private Integer seatPlan;
	private Integer carNum;
	private Integer seatTypeId;
	private String seatTypeName;
	public Integer getSeatPlan() {
		return seatPlan;
	}
	public void setSeatPlan(Integer seatPlan) {
		this.seatPlan = seatPlan;
	}
	public Integer getCarNum() {
		return carNum;
	}
	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}
	public Integer getSeatTypeId() {
		return seatTypeId;
	}
	public void setSeatTypeId(Integer seatTypeId) {
		this.seatTypeId = seatTypeId;
	}
	public String getSeatTypeName() {
		return seatTypeName;
	}
	public void setSeatTypeName(String seatTypeName) {
		this.seatTypeName = seatTypeName;
	}
}