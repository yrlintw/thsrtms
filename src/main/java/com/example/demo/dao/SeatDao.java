package com.example.demo.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.demo.dto.SeatPlanQueryParams;
import com.example.demo.model.SeatPlan;
import com.example.demo.model.SeatType;

public interface SeatDao {

	public ArrayList<SeatPlan> getSeatPlan(SeatPlanQueryParams seatPlanQueryParams);

	public ArrayList<Integer> getNonReservedCarNums(Integer trainNum, LocalDate runDate);

	public ArrayList<SeatType> getSeatTypes();

	
}
