package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.SeatDao;
import com.example.demo.dto.SeatPlanQueryParams;
import com.example.demo.model.SeatPlan;
import com.example.demo.model.SeatPreference;
import com.example.demo.model.SeatType;

@Component
public class SeatServiceImpl implements SeatService {

	@Autowired
	private SeatDao seatDao;
	
	@Override
	public ArrayList<SeatPlan> getSeatPlan(SeatPlanQueryParams seatPlanQueryParams) {		
		return seatDao.getSeatPlan(seatPlanQueryParams);
	}

	@Override
	public ArrayList<SeatType> getSeatTypes() {
		return seatDao.getSeatTypes();
	}

	@Override
	public ArrayList<SeatPreference> getSeatPreferences() {
		
		String[] seatPreferenceEngName = new String[] {"", "window", "aisle"};
		String[] seatPreferenceName = new String[] {"無", "靠窗", "走道"};
		ArrayList<SeatPreference> list = new ArrayList<>();
		
		for (int i = 0; i < seatPreferenceEngName.length; i++) {
			SeatPreference seatPreference= new SeatPreference();
			seatPreference.setSeatPreferenceEngName(seatPreferenceEngName[i]);
			seatPreference.setSeatPreferenceName(seatPreferenceName[i]);
			list.add(seatPreference);
		}
		
		return list;
	}

	@Override
	public ArrayList<Integer> getNonReservedCarNums(Integer trainNum, LocalDate runDate) {
		return seatDao.getNonReservedCarNums(trainNum, runDate);
	}
}
