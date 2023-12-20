package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.Station;

public interface StationService {

	ArrayList<Station> getStations();
	
	Station getStationById(Integer stationId);

}
