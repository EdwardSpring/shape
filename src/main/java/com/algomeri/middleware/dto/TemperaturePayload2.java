package com.algomeri.middleware.dto;

import java.util.List;

public class TemperaturePayload2 {
	private long locationId;
	private String city;
	private String country;
	private List<TemperaturePayloadData> days;
	
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
	public List<TemperaturePayloadData> getDays() {
		return days;
	}
	public void setDays(List<TemperaturePayloadData> days) {
		this.days = days;
	}
	
	

}
