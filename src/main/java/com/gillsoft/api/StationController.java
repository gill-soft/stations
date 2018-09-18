package com.gillsoft.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gillsoft.entity.Station;
import com.gillsoft.entity.StationResourceMap;
import com.gillsoft.service.impl.StationServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StationController {

	@Autowired
    private StationServiceImpl stationService;

	@GetMapping()
	@ApiOperation("Get all stations")
	public ResponseEntity<List<Station>> getAll() {
		List<Station> stations = stationService.getAll();
		return new ResponseEntity<List<Station>>(stations, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation("Get one station by it's key (id)")
	public ResponseEntity<Station> getOne(@PathVariable("id") int stationId) {
		Station station = stationService.findOne(stationId);
		if (station == null) {
			return new ResponseEntity<Station>(new Station(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Station>(station, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation("Create new station")
	public ResponseEntity<Station> create(@RequestBody Station station) {
		if (station.getStationLangs() != null && !station.getStationLangs().isEmpty()) {
			station.getStationLangs().stream().forEach(c -> c.setStation(station));
		}
		return new ResponseEntity<Station>(stationService.save(station), HttpStatus.OK);
	}

	@PutMapping
	@ApiOperation("Update existing station's data")
	public ResponseEntity<Station> update(@RequestBody Station station) {
		if (station.getStationId() == null) {
			return new ResponseEntity<Station>(station, HttpStatus.BAD_REQUEST);
		}
		Station currentStation = stationService.findOne(station.getStationId());
		if (currentStation != null) {
			currentStation.setLatitude(station.getLatitude());
			currentStation.setLongitude(station.getLongitude());
			currentStation.setParentLocation(station.getParentLocation());
			currentStation.setStationLangs(station.getStationLangs());
			currentStation.setStationResourceMaps(station.getStationResourceMaps());
		}
		if (currentStation.getStationLangs() != null && !currentStation.getStationLangs().isEmpty()) {
			currentStation.getStationLangs().stream().forEach(c -> c.setStation(currentStation));
		}
		if (currentStation.getStationResourceMaps() != null && !currentStation.getStationResourceMaps().isEmpty()) {
			currentStation.getStationResourceMaps().stream().forEach(c -> c.setStation(currentStation));
		}
		stationService.update(currentStation);
		return new ResponseEntity<Station>(currentStation, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Delete station by it's key (id)")
	public ResponseEntity<String> delete(@PathVariable("id") int stationId) {
		Station station = stationService.findOne(stationId);
		if (station != null) {
			stationService.delete(station);
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/{id}/map")
	@ApiOperation("Stations mapping (resource -> reference)")
	public ResponseEntity<Station> createMap(@PathVariable("id") int stationId, @RequestBody Station station) {
		final Station currentStation = stationService.findOne(stationId);
		if (currentStation != null) {
			currentStation.setStationResourceMaps(station.getStationResourceMaps());
			if (currentStation.getStationResourceMaps() != null && !currentStation.getStationResourceMaps().isEmpty()) {
				currentStation.getStationResourceMaps().stream().forEach(c -> c.setStation(currentStation));
			}
			stationService.update(currentStation);
			return new ResponseEntity<Station>(stationService.findOne(stationId), HttpStatus.OK);
		}
		return new ResponseEntity<Station>(station, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}/map/{resourceId}/{resourceStationId}")
	@ApiOperation("Delete resource station's mapping (resource ≠ reference)")
	public ResponseEntity<Station> updateMap(@PathVariable("id") int stationId,
			@PathVariable("resourceId") int resourceId, @PathVariable("resourceStationId") String resourceStationId) {
		final Station currentStation = stationService.findOne(stationId);
		if (currentStation != null && currentStation.getStationResourceMaps() != null
				&& !currentStation.getStationResourceMaps().isEmpty()) {
			StationResourceMap stationResourceMap = new StationResourceMap();
			stationResourceMap.setStation(currentStation);
			stationResourceMap.setResourceId(resourceId);
			stationResourceMap.setResourceStationId(resourceStationId);
			stationService.removeResourceMap(stationResourceMap);
			return new ResponseEntity<Station>(stationService.findOne(stationId), HttpStatus.OK);
		}
		return new ResponseEntity<Station>(currentStation, HttpStatus.NOT_FOUND);
	}
}
