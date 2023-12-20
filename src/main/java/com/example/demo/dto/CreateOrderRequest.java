package com.example.demo.dto;

import java.time.LocalDate;

public class CreateOrderRequest {
	
	public CreateOrderRequest() {
		super();
		this.orderId = -1;
	}

	//訂單id(未創建時，預設為-1)
	private Integer orderId;
	private Integer trainNumId;
	private Integer trainNum;
	private LocalDate runDate;
	private Integer stationStartId;
	private Integer stationEndId;
	private Integer seatTypeId;
	private Integer ticketTypeId;
	private String seatPreference;
	private Integer quantity;

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
	public Integer getStationEndId() {
		return stationEndId;
	}
	public void setStationEndId(Integer stationEndId) {
		this.stationEndId = stationEndId;
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
	public String getSeatPreference() {
		return seatPreference;
	}
	public void setSeatPreference(String seatPreference) {
		this.seatPreference = seatPreference;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getTrainNumId() {
		return trainNumId;
	}
	public void setTrainNumId(Integer trainNumId) {
		this.trainNumId = trainNumId;
	}
}