package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.SeatPlanQueryParams;
import com.example.demo.model.SeatPlan;
import com.example.demo.service.SeatService;
import com.example.demo.util.Page;

@Controller
@Validated
public class SeatController {

	@Autowired
	private SeatService seatService;
	
	@GetMapping("/seats/seatplan")
	public ResponseEntity<Page<SeatPlan>> read(@RequestParam(required = true) Integer trainnum, 
			@RequestParam(required = true) LocalDate rundate) {
		
		SeatPlanQueryParams seatPlanQueryParams = new SeatPlanQueryParams();
		seatPlanQueryParams.setTrainNum(trainnum);
		seatPlanQueryParams.setRunDate(rundate);
		
		ArrayList<SeatPlan> list = seatService.getSeatPlan(seatPlanQueryParams);
		
		Page<SeatPlan> page = new Page<>();
		page.setResults(list);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
}
