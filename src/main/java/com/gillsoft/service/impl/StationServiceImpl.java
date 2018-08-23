package com.gillsoft.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gillsoft.entity.Station;
import com.gillsoft.entity.StationResourceMap;
import com.gillsoft.repository.StationRepository;
import com.gillsoft.service.StationService;

@Service
@Component
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository repository;
    
    @Autowired
    protected LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Override
	public List<Station> getAll() {
		return repository.findAll();
	}

	@Override
	public Station findOne(Integer stationId) {
		return repository.findOne(stationId);
	}
	
	@Override
	public Station save(Station station) {
		return repository.save(station);
	}
	
	public void delete(Station station) {
		repository.delete(station);
	}
	
	public void update(Station station) {
		Session session = entityManagerFactory.getObject().unwrap(SessionFactory.class).openSession();
	    session.beginTransaction();
	    session.update(station);
	    if (station.getStationResourceMaps() != null && !station.getStationResourceMaps().isEmpty()) {
	    	for (StationResourceMap stationResourceMap : station.getStationResourceMaps()) {
	    		session.update(stationResourceMap);
	    	}
	    }
	    session.flush();
	    session.getTransaction().commit();
	    session.close();
	}

	@Modifying
	public void removeResourceMap(StationResourceMap stationResourceMap) {
		repository.deleteResourceMap(stationResourceMap.getStation().getStationId(), stationResourceMap.getResourceId(), stationResourceMap.getResourceStationId());
	}

}
