package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.SeatPreference;
import com.example.demo.model.SeatType;
import com.example.demo.model.Station;
import com.example.demo.model.TicketType;
import com.example.demo.model.TrainSchedule;
import com.example.demo.service.SeatService;
import com.example.demo.service.StationService;
import com.example.demo.service.TicketSerivce;
import com.example.demo.service.TrainService;

@Controller
@Validated
public class PageController {
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private TicketSerivce ticketSerivce;
	
	@Autowired
	private SeatService seatService;

	@Autowired
	private TrainService trainService;
	
	@GetMapping("/home")
	public String home(Model model) {
		
		ArrayList<Station> stations = stationService.getStations();
		
		model.addAttribute("stations", stations);
		
		return "home";
	}
	
	@GetMapping("/order")
	public String order(@RequestParam(required = true) Integer trainNum,
			@RequestParam(required = true) LocalDate runDate,
			@RequestParam(required = true) Integer stationStartId,
			@RequestParam(required = true) Integer stationEndId,
			Model model) {
		
		model.addAttribute("trainNum", trainNum);
		model.addAttribute("runDate", runDate);
		
		ArrayList<TrainSchedule> list = trainService.getTrainScheduleByTrainNum(trainNum, runDate);
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStationId().equals(stationStartId)) {
				model.addAttribute("stationStartId", list.get(i).getStationId());
				model.addAttribute("stationStartName", list.get(i).getStationName());
				model.addAttribute("departureTime", list.get(i).getTime());
			
			} else if (list.get(i).getStationId().equals(stationEndId)) {
				model.addAttribute("stationEndId", list.get(i).getStationId());
				model.addAttribute("stationEndName", list.get(i).getStationName());
				model.addAttribute("arrivalTime", list.get(i).getTime());
			}
		}
		
		ArrayList<TicketType> ticketTypes = ticketSerivce.getTypes();
		ArrayList<SeatType> seatTypes = seatService.getSeatTypes();
		
		for (int i = 0; i < seatTypes.size(); i++) {
			if (seatTypes.get(i).getSeatTypeName().equals("自由座")) {
				seatTypes.remove(i);
			}
		}
		
		ArrayList<SeatPreference> seatPreferences = seatService.getSeatPreferences();

		model.addAttribute("ticketTypes", ticketTypes);
		model.addAttribute("seatTypes", seatTypes);
		model.addAttribute("seatPreferences", seatPreferences);
		model.addAttribute("createOrderRequest", new CreateOrderRequest());
		
		return "order";
	}

}
