package com.algomeri.middleware.dto;

import java.util.List;

public class TemperaturePayload {
	private long locationId;
	private String city;
	private String country;
	private List<TemperaturePayloadData> temperatures;

	public TemperaturePayload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TemperaturePayload(int locationId, String city, String country, List<TemperaturePayloadData> temperatures) {
		super();
		this.locationId = locationId;
		this.city = city;
		this.country = country;
		this.temperatures = temperatures;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<TemperaturePayloadData> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(List<TemperaturePayloadData> temperatures) {
		this.temperatures = temperatures;
	}

	

}
