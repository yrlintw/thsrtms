package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.dto.CreateTicketPriceRequest;
import com.example.demo.dto.CreateTicketTypeRequest;
import com.example.demo.dto.TicketQueryParams;
import com.example.demo.model.Ticket;
import com.example.demo.model.TicketType;

public interface TicketSerivce {

	public ArrayList<Ticket> getTicketPrice(TicketQueryParams ticketQueryParams);

	public Integer create(CreateTicketPriceRequest createTicketPriceRequest);

	public void updateTicketPrice(CreateTicketPriceRequest createTicketPriceRequest);

	public void deleteTicketPriceById(Integer ticketPriceId);

	public Integer createType(CreateTicketTypeRequest createTicketTypeRequest);

	public ArrayList<TicketType> getTypes();

	public void updateType(Integer ticketTypeId, CreateTicketTypeRequest createTicketTypeRequest);

	public void deleteType(Integer ticketTypeId);

}
