package com.example.demo.dao;

import java.util.ArrayList;

import com.example.demo.model.Station;

public interface StationDao {

	ArrayList<Station> getStations();

	Station getStationById(Integer stationId);

}
