package com.gillsoft.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="stations")
@NamedQuery(name="Station.findAll", query="SELECT s FROM Station s")
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({"station_id", "parent_location", "latitude", "longitude", "langs", "resource_map"})
public class Station implements Serializable {

	private static final long serialVersionUID = -1052211220608317422L;

	@Id
	@Column(name="station_id", unique=true, nullable=false)
	@GeneratedValue
	@JsonProperty("station_id")
	private Integer stationId;

	private BigDecimal latitude;

	private BigDecimal longitude;

	@Column(name="parent_location", nullable=false)
	@JsonProperty("parent_location")
	private Integer parentLocation;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "station", orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JsonProperty("langs")
	private List<StationLang> stationLangs;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "station", orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JsonProperty("resource_map")
	private List<StationResourceMap> stationResourceMaps;

	public Station() {
	}

	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Integer getParentLocation() {
		return this.parentLocation;
	}

	public void setParentLocation(Integer parentLocation) {
		this.parentLocation = parentLocation;
	}

	public List<StationLang> getStationLangs() {
		return this.stationLangs;
	}

	public void setStationLangs(List<StationLang> stationLangs) {
		this.stationLangs = stationLangs;
	}

	public StationLang addStationLang(StationLang stationLang) {
		stationLangs.add(stationLang);
		stationLang.setStation(this);
		return stationLang;
	}

	public StationLang removeStationLang(StationLang stationLang) {
		getStationLangs().remove(stationLang);
		stationLang.setStation(null);
		return stationLang;
	}

	public List<StationResourceMap> getStationResourceMaps() {
		return this.stationResourceMaps;
	}

	public void setStationResourceMaps(List<StationResourceMap> stationResourceMaps) {
		this.stationResourceMaps = stationResourceMaps;
	}

	public StationResourceMap addStationResourceMap(StationResourceMap stationResourceMap) {
		stationResourceMaps.add(stationResourceMap);
		stationResourceMap.setStation(this);
		return stationResourceMap;
	}

	public StationResourceMap removeStationResourceMap(StationResourceMap stationResourceMap) {
		getStationResourceMaps().remove(stationResourceMap);
		stationResourceMap.setStation(null);
		return stationResourceMap;
	}

}