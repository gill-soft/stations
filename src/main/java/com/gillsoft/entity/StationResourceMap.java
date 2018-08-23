package com.gillsoft.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="station_resource_map")
@NamedQuery(name="StationResourceMap.findAll", query="SELECT s FROM StationResourceMap s")
public class StationResourceMap implements Serializable {

	private static final long serialVersionUID = 4786026064668198887L;

	@Id
	@Column(name="resource_id", nullable=false)
	@JsonProperty("resource_id")
	private Integer resourceId;

	@Id
	@Column(name="resource_station_id", nullable=false, length=64)
	@JsonProperty("resource_station_id")
	private String resourceStationId;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="station_id", referencedColumnName = "station_id", nullable = false)
	private Station station;

	public StationResourceMap() {
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceStationId() {
		return this.resourceStationId;
	}

	public void setResourceStationId(String resourceStationId) {
		this.resourceStationId = resourceStationId;
	}

	public Station getStation() {
		return this.station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

}