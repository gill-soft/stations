package com.gillsoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gillsoft.entity.Station;

@Repository
public interface StationRepository extends CrudRepository<Station, Integer> {

	@Override
	@Query("select s from Station s")
	List<Station> findAll();

	@Override
	Station findOne(Integer stationId);
	
	@Query("delete from StationResourceMap where station_id = :p1 and resource_id = :p2 and resource_station_id = :p3")
	@Modifying
	@Transactional
	void deleteResourceMap(@Param("p1") int stationId, @Param("p2") int resourceId, @Param("p3") String resourceStationId);

}