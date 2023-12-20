package com.example.demo.model;

public class Seat {
	
	private Integer seatNumId;
	private Integer seatTypeId;
	private Integer carNum;
	private Integer rowNum;
	private String colLetter;
	public Integer getSeatNumId() {
		return seatNumId;
	}
	public void setSeatNumId(Integer seatNumId) {
		this.seatNumId = seatNumId;
	}
	public Integer getSeatTypeId() {
		return seatTypeId;
	}
	public void setSeatTypeId(Integer seatTypeId) {
		this.seatTypeId = seatTypeId;
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
}
