package com.gillsoft.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gillsoft.entity.Station;

@Service	
@Component
public interface StationService {
	
	public List<Station> getAll();
	
	public Station findOne(Integer stationId);
	
	public Station save(Station station);
	
}
