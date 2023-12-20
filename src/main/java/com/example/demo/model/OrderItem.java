package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderItem {
	private Integer orderItemId;
	private Integer orderId;
	
	private Integer trainNumId;
	private LocalDate runDate;
	private Integer trainNum;
	
	private Integer stationStartId;
	private String stationStartName;
	private LocalTime startTime;
	private Integer stationEndId;
	private String stationEndName;
	private LocalTime endTime;
	private LocalTime travelTime;
	
	private Integer seatTypeId;
	private String seatTypeName;
	
	private Integer seatNumId;
	private Integer carNum;
	private Integer rowNum;
	private String colLetter;
	
	private Integer ticketTypeId;
	private String ticketTypeName;
	
	private Integer amount;
	public Integer getTrainNumId() {
		return trainNumId;
	}
	public void setTrainNumId(Integer trainNumId) {
		this.trainNumId = trainNumId;
	}
	public Integer getSeatTypeId() {
		return seatTypeId;
	}
	public void setSeatTypeId(Integer seatTypeId) {
		this.seatTypeId = seatTypeId;
	}
	public Integer getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(Integer ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public LocalDate getRunDate() {
		return runDate;
	}
	public void setRunDate(LocalDate runDate) {
		this.runDate = runDate;
	}
	public Integer getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(Integer trainNum) {
		this.trainNum = trainNum;
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
	public LocalTime getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(LocalTime travelTime) {
		this.travelTime = travelTime;
	}
	public String getSeatTypeName() {
		return seatTypeName;
	}
	public void setSeatTypeName(String seatTypeName) {
		this.seatTypeName = seatTypeName;
	}
	public Integer getCarNum() {
		return carNum;
	}
	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public String getColLetter() {
		return colLetter;
	}
	public void setColLetter(String colLetter) {
		this.colLetter = colLetter;
	}
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Integer getSeatNumId() {
		return seatNumId;
	}
	public void setSeatNumId(Integer seatNumId) {
		this.seatNumId = seatNumId;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
}
