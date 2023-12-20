package com.example.demo.controller;

import java.util.ArrayList;

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

import com.example.demo.dto.CreateTicketPriceRequest;
import com.example.demo.dto.CreateTicketTypeRequest;
import com.example.demo.dto.TicketQueryParams;
import com.example.demo.model.Ticket;
import com.example.demo.model.TicketType;
import com.example.demo.service.TicketSerivce;
import com.example.demo.util.Page;

import jakarta.validation.Valid;

@Controller
@Validated
public class TicketController {

	@Autowired
	private TicketSerivce ticketService;
	
	@PostMapping("/tickets")
	public ResponseEntity<?> create(@RequestBody @Valid CreateTicketPriceRequest createTicketPriceRequest) {
		
		ticketService.create(createTicketPriceRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/tickets")
	public String read(
			@RequestParam (required = true) Integer from, 
			@RequestParam (required = true) Integer to, 
			Model model) {
		
		TicketQueryParams ticketQueryParams = new TicketQueryParams();
		ticketQueryParams.setStationStartId(from);
		ticketQueryParams.setStationEndId(to);

		ArrayList<Ticket> list = ticketService.getTicketPrice(ticketQueryParams);
		
		Page<Ticket> page = new Page<>();
		page.setResults(list);
		
		model.addAttribute("page", page);
		
		return "tickettable";
	}
	
	@PutMapping("/tickets")
	public ResponseEntity<?> update(@RequestBody @Valid CreateTicketPriceRequest createTicketPriceRequest) {
		
		ticketService.updateTicketPrice(createTicketPriceRequest);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/tickets/{ticketPriceId}")
	public ResponseEntity<?> delete(@PathVariable Integer ticketPriceId) {
		
		ticketService.deleteTicketPriceById(ticketPriceId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping("/tickets/type")
	public ResponseEntity<?> createType(@RequestBody CreateTicketTypeRequest createTicketTypeRequest) {
		
		ticketService.createType(createTicketTypeRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/tickets/type")
	public ResponseEntity<Page<TicketType>> readType() {
		
		ArrayList<TicketType> types = ticketService.getTypes();
		
		Page<TicketType> page = new Page<>();
		page.setResults(types);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
	
	@PutMapping("/tickets/type/{ticketTypeId}")
	public ResponseEntity<?> updateType(@PathVariable Integer ticketTypeId, 
			@RequestBody CreateTicketTypeRequest createTicketTypeRequest) {
		
		ticketService.updateType(ticketTypeId, createTicketTypeRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/tickets/type/{ticketTypeId}")
	public ResponseEntity<?> deleteType(@PathVariable Integer ticketTypeId) {
		
		ticketService.deleteType(ticketTypeId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
