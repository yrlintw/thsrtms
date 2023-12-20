package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Station;
import com.example.demo.service.StationService;
import com.example.demo.util.Page;

@Controller
@Validated
public class StationController {
	
	@Autowired
	private StationService stationService;

	@GetMapping("/stations")
	public ResponseEntity<Page<Station>> read() {
		
		ArrayList<Station> list = stationService.getStations();
		Page<Station> page = new Page<>();
		page.setResults(list);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
}
