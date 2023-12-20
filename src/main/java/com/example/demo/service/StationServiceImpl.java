package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.StationDao;
import com.example.demo.model.Station;

@Component
public class StationServiceImpl implements StationService {

	@Autowired
	private StationDao stationDao;
	
	@Override
	public ArrayList<Station> getStations() {
		return stationDao.getStations();
	}

	@Override
	public Station getStationById(Integer stationId) {
		return stationDao.getStationById(stationId);
	}

	
}
