package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.CreateTrainNumRequest;
import com.example.demo.dto.CreateTrainScheduleRequest;
import com.example.demo.dto.TrainNumQueryParams;
import com.example.demo.dto.TrainQueryParams;
import com.example.demo.model.TrainNum;
import com.example.demo.model.TrainSchedule;
import com.example.demo.model.TrainTimeTable;
import com.example.demo.service.SeatService;
import com.example.demo.service.StationService;
import com.example.demo.service.TrainService;
import com.example.demo.util.Page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Controller
@Validated
public class TrainController {

	@Autowired
	private TrainService trainService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private SeatService seatService;
	
	@PostMapping("/trains/trainnum")
	public ResponseEntity<?> createTrainNum(@RequestBody CreateTrainNumRequest createTrainNumRequest) {
		
		trainService.register(createTrainNumRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/trains/trainnum")
	public ResponseEntity<Page<TrainNum>> readTrainNum(
			@RequestParam (required = false) Integer trainNum,
			@RequestParam (required = false) LocalDate runDate) {
		
		TrainNumQueryParams trainNumQueryParams = new TrainNumQueryParams();
		trainNumQueryParams.setTrainNum(trainNum);
		trainNumQueryParams.setRunDate(runDate);
		
		ArrayList<TrainNum> list = trainService.getTrainNums(trainNumQueryParams);
		
		if (list != null) {
			Page<TrainNum> page = new Page<>();
			page.setResults(list);
			
			return ResponseEntity.status(HttpStatus.OK).body(page);
			
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	//限更新seat_plan代碼
	@PutMapping("/trains/trainnum")
	public ResponseEntity<?> updateTrainNum(@RequestBody CreateTrainNumRequest createTrainNumRequest) {
		
		trainService.updateTrainNumSeatPlan(createTrainNumRequest);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/trains/trainnum/{trainNumId}")
	public ResponseEntity<Page<TrainNum>> deleteTrainNum(@PathVariable Integer trainNumId) {
		
		trainService.deleteTrainNumById(trainNumId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping("/trains/trainschedule")
	public ResponseEntity<Page<TrainSchedule>> createTrainSchedule(@RequestBody CreateTrainScheduleRequest createTrainScheduleRequest) {
		
		trainService.createTrainSchedule(createTrainScheduleRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/trains/trainschedule")
	public String readTrainSchedule(@RequestParam(required = true) Integer trainNum, 
			@RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}", required = true) LocalDate runDate, 
			Model model) {

		ArrayList<TrainSchedule> list = trainService.getTrainScheduleByTrainNum(trainNum, runDate);
		ArrayList<Integer> nonReservedCarNums = seatService.getNonReservedCarNums(trainNum, runDate);
		
		Page<TrainSchedule> page = new Page<>();
		page.setResults(list);
		
		model.addAttribute("page", page);
		model.addAttribute("trainNum", trainNum);
		model.addAttribute("runDate", runDate);
		model.addAttribute("nonReservedCarNums", nonReservedCarNums.stream().map(String::valueOf).collect(Collectors.joining(", ")));
		
		return "schedules";
	}
	
	//限更新到站時間
	@PutMapping("/trains/trainschedule")
	public ResponseEntity<?> updateTrainSchedule(@RequestBody TrainSchedule trainSchedule) {
		
		trainService.updateTrainScheduleTime(trainSchedule);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/trains/trainschedule/{trainNum}")
	public ResponseEntity<?> deleteTrainSchedule(@PathVariable Integer trainNum) {
		
		trainService.deleteTrainScheduleByTrainNum(trainNum);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/trains/timetable")
	public String readTimeTable(
			@RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}", required = false) LocalDate departureDate,
			@RequestParam(defaultValue = "1", required = false) Integer stationStartId,
			@RequestParam(defaultValue = "12", required = false) Integer stationEndId,
			@RequestParam(defaultValue = "#{T(java.time.LocalTime).now()}", required = false) LocalTime departureTime,
			@RequestParam(defaultValue = "6", required = false) @Max(100) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0", required = false) @Min(0) Integer offset,
			Model model) {
		
		TrainQueryParams trainQueryParams = new TrainQueryParams();
		trainQueryParams.setRunDate(departureDate);
		trainQueryParams.setStationStartId(stationStartId);
		trainQueryParams.setStationEndId(stationEndId);
		trainQueryParams.setDepartureHour(departureTime.getHour());
		trainQueryParams.setDepartureMin(departureTime.getMinute());
		trainQueryParams.setLimit(limit);
		trainQueryParams.setOffset(offset);
		
		ArrayList<TrainTimeTable> list = trainService.getTrainTimeTable(trainQueryParams);
		Integer total = trainService.countTrainTimeTable(trainQueryParams);
		
		//station id
		model.addAttribute("stationStartId", stationStartId);
		model.addAttribute("stationEndId", stationEndId);
		
		//station name
		model.addAttribute("stationStartName", stationService.getStationById(stationStartId).getStationName());
		model.addAttribute("stationEndName", stationService.getStationById(stationEndId).getStationName());
		
		//date and time
		model.addAttribute("date", departureDate);
		model.addAttribute("time", departureTime);
		
		Page<TrainTimeTable> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setCurrent((offset / limit) + 1);
		page.setPages((total % limit == 0)? total / limit : (total / limit) + 1);
		page.setResults(list);
		
		model.addAttribute("page", page);
		
		return "timetable";
	}
	
	@GetMapping("/trains/arrivaltimetable")
	public String readArrivalTimeTable(
			@RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}", required = false) LocalDate departureDate,
			@RequestParam(defaultValue = "1", required = false) Integer stationStartId,
			@RequestParam(defaultValue = "12", required = false) Integer stationEndId,
			@RequestParam(defaultValue = "#{T(java.time.LocalTime).now()}", required = false) LocalTime arrivalTime,
			@RequestParam(defaultValue = "6", required = false) @Max(100) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0", required = false) @Min(0) Integer offset,
			Model model) {
		
		TrainQueryParams trainQueryParams = new TrainQueryParams();
		trainQueryParams.setRunDate(departureDate);
		trainQueryParams.setStationStartId(stationStartId);
		trainQueryParams.setStationEndId(stationEndId);
		trainQueryParams.setArrivalHour(arrivalTime.getHour());
		trainQueryParams.setArrivalMin(arrivalTime.getMinute());
		trainQueryParams.setLimit(limit);
		trainQueryParams.setOffset(offset);
		
		ArrayList<TrainTimeTable> list = trainService.getArrivalTrainTimeTable(trainQueryParams);
		Integer total = trainService.countArrivalTrainTimeTable(trainQueryParams);
		
		//station id
		model.addAttribute("stationStartId", stationStartId);
		model.addAttribute("stationEndId", stationEndId);
		
		//station name
		model.addAttribute("stationStartName", stationService.getStationById(stationStartId).getStationName());
		model.addAttribute("stationEndName", stationService.getStationById(stationEndId).getStationName());
		
		//date and time
		model.addAttribute("date", departureDate);
		model.addAttribute("time", arrivalTime);
		
		Page<TrainTimeTable> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setCurrent((offset / limit) + 1);
		page.setPages((total % limit == 0)? total / limit : (total / limit) + 1);
		page.setResults(list);
		
		model.addAttribute("page", page);
		
		return "arrival-timetable";
	}
	
}
