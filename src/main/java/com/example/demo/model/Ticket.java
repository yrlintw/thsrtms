package com.example.demo.model;

public class Ticket {

	private Integer ticketPriceId;
	private Integer stationStartId;
	private String stationStartName;
	private Integer stationEndId;
	private String stationEndName;
	private Integer ticketTypeId;
	private String ticketTypeName;
	private Integer seatTypeId;
	private String seatTypeName;
	private Integer price;
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
	public Integer getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(Integer ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getTicketPriceId() {
		return ticketPriceId;
	}
	public void setTicketPriceId(Integer ticketPriceId) {
		this.ticketPriceId = ticketPriceId;
	}

}
