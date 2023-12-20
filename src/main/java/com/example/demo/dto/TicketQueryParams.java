package com.example.demo.dto;

public class TicketQueryParams {

	private Integer ticketTypeId;
	private Integer seatTypeId;
	private Integer stationStartId;
	private Integer stationEndId;
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
}
