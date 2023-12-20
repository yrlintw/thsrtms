package com.example.demo.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.TicketDao;
import com.example.demo.dto.CreateTicketPriceRequest;
import com.example.demo.dto.CreateTicketTypeRequest;
import com.example.demo.dto.TicketQueryParams;
import com.example.demo.model.Ticket;
import com.example.demo.model.TicketType;

@Component
public class TicketSerivceImpl implements TicketSerivce {

	private final static Logger log = LoggerFactory.getLogger(TicketSerivceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	
	@Override
	public ArrayList<Ticket> getTicketPrice(TicketQueryParams ticketQueryParams) {
		
		Boolean swap = false;
		if (ticketQueryParams.getStationStartId() > ticketQueryParams.getStationEndId()) {
			Integer temp = ticketQueryParams.getStationStartId();
			ticketQueryParams.setStationStartId(ticketQueryParams.getStationEndId());
			ticketQueryParams.setStationEndId(temp);
			swap = true;
		}
		
		ArrayList<Ticket> list = ticketDao.getTicketPrice(ticketQueryParams);
		
		if (swap) {
			Integer temp = 0;
			String tempStr = "";
			for (int i = 0; i < list.size(); i++) {
				temp = list.get(i).getStationStartId();
				list.get(i).setStationStartId(list.get(i).getStationEndId());
				list.get(i).setStationEndId(temp);
				
				tempStr = list.get(i).getStationStartName();
				list.get(i).setStationStartName(list.get(i).getStationEndName());
				list.get(i).setStationEndName(tempStr);
			}
		}
		
		return list;
	}

	@Override
	public Integer create(CreateTicketPriceRequest createTicketPriceRequest) {
		
		if (createTicketPriceRequest.getStationStartId() > createTicketPriceRequest.getStationEndId()) {
			Integer temp = createTicketPriceRequest.getStationStartId();
			createTicketPriceRequest.setStationStartId(createTicketPriceRequest.getStationEndId());
			createTicketPriceRequest.setStationEndId(temp);
		}
		
		TicketQueryParams ticketQueryParams = new TicketQueryParams();
		ticketQueryParams.setTicketTypeId(createTicketPriceRequest.getTicketTypeId());
		ticketQueryParams.setSeatTypeId(createTicketPriceRequest.getSeatTypeId());
		ticketQueryParams.setStationStartId(createTicketPriceRequest.getStationStartId());
		ticketQueryParams.setStationEndId(createTicketPriceRequest.getStationEndId());
		
		ArrayList<Ticket> list = ticketDao.getTicketPrice(ticketQueryParams);
		if (list.size() > 0) {
			log.warn("車票金額資料已存在");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Integer id = ticketDao.create(createTicketPriceRequest);
		return id;
	}

	@Override
	public void updateTicketPrice(CreateTicketPriceRequest createTicketPriceRequest) {
		
		if (createTicketPriceRequest.getStationStartId() > createTicketPriceRequest.getStationEndId()) {
			Integer temp = createTicketPriceRequest.getStationStartId();
			createTicketPriceRequest.setStationStartId(createTicketPriceRequest.getStationEndId());
			createTicketPriceRequest.setStationEndId(temp);
		}
		ticketDao.updateTicketPrice(createTicketPriceRequest);
	}

	@Override
	public void deleteTicketPriceById(Integer ticketPriceId) {
		ticketDao.daleteTicketPrice(ticketPriceId);		
	}

	@Override
	public Integer createType(CreateTicketTypeRequest createTicketTypeRequest) {
		
		ArrayList<TicketType> types = ticketDao.getTypes();
		if (containsName(types, createTicketTypeRequest.getTicketTypeName())) {
			log.warn("車票類型 {} 已存在", createTicketTypeRequest.getTicketTypeName());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		return ticketDao.createType(createTicketTypeRequest);
	}

	@Override
	public ArrayList<TicketType> getTypes() {
		return ticketDao.getTypes();
	}
	
	
	private boolean containsName(final ArrayList<TicketType> list, final String name) {
    	return list.stream().filter(o -> o.getTicketTypeName().equals(name)).findFirst().isPresent();
	}

	@Override
	public void updateType(Integer ticketTypeId, CreateTicketTypeRequest createTicketTypeRequest) {
		
		ArrayList<TicketType> types = ticketDao.getTypes();
		if (containsName(types, createTicketTypeRequest.getTicketTypeName())) {
			log.warn("車票類型 {} 已存在", createTicketTypeRequest.getTicketTypeName());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		ticketDao.updateType(ticketTypeId, createTicketTypeRequest);
	}

	@Override
	public void deleteType(Integer ticketTypeId) {
		ticketDao.deleteType(ticketTypeId);
	}

}
