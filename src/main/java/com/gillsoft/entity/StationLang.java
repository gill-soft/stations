package com.gillsoft.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="station_lang")
@NamedQuery(name="StationLang.findAll", query="SELECT s FROM StationLang s")
@JsonInclude(value = Include.NON_NULL)
public class StationLang implements Serializable {

	private static final long serialVersionUID = 9109318105618342431L;

	@Column(length=255)
	private String address;

	@Id
	@Column(nullable=false, length=2)
	private String lang;

	@Column(nullable=false, length=255)
	private String name;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name="station_id", referencedColumnName = "station_id", nullable = false)
	private Station station;

	public StationLang() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}*/

	public Station getStation() {
		return this.station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
}