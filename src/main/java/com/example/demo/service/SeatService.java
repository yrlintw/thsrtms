package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.demo.dto.SeatPlanQueryParams;
import com.example.demo.model.SeatPlan;
import com.example.demo.model.SeatPreference;
import com.example.demo.model.SeatType;

public interface SeatService {

	public ArrayList<SeatPlan> getSeatPlan(SeatPlanQueryParams seatPlanQueryParams);

	public ArrayList<SeatType> getSeatTypes();
	
	public ArrayList<Integer> getNonReservedCarNums(Integer trainNum, LocalDate runDate);
	
	public ArrayList<SeatPreference> getSeatPreferences();
}
