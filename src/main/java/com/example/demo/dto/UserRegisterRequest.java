package com.example.demo.dto;

import jakarta.validation.constraints.Email;

public class UserRegisterRequest {
	private Integer userId;
	@Email
	private String email;
	private String password;
	private String name;
	private String identification;
	private String passportArc;
	private String phone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassportArc() {
		return passportArc;
	}
	public void setPassportArc(String passportArc) {
		this.passportArc = passportArc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}