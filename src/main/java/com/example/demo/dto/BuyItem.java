package com.example.demo.dto;

public class BuyItem {

	//車票內容
	private Integer trainNumId;
	private Integer stationStartId;
	private Integer stationEndId;
	private Integer seatTypeId;
	private Integer ticketTypeId;
	//座位偏好
	private String seatPreference;
	//訂票數量
	private Integer quantity;
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
	public Integer getStationEndId() {
		return stationEndId;
	}
	public void setStationEndId(Integer stationEndId) {
		this.stationEndId = stationEndId;
	}
	public Integer getStationStartId() {
		return stationStartId;
	}
	public void setStationStartId(Integer stationStartId) {
		this.stationStartId = stationStartId;
	}
}