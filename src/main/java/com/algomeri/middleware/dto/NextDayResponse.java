package com.algomeri.middleware.dto;

import java.util.List;

public class NextDayResponse {
	private long cityId;
	private String city;
	private List<TemperaturePayloadData> temperatures;
	
	
	
	
	public NextDayResponse(long cityId, String city, List<TemperaturePayloadData> temperatures) {
		super();
		this.cityId = cityId;
		this.city = city;
		this.temperatures = temperatures;
	}
	public NextDayResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<TemperaturePayloadData> getTemperatures() {
		return temperatures;
	}
	public void setTemperatures(List<TemperaturePayloadData> temperatures) {
		this.temperatures = temperatures;
	}
	
	

}
