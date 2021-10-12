package com.algomeri.middleware.dto;

import java.util.List;

public class TemperaturePayload2Data {
	private String day;
	private List<TemperaturePayload2DataData> temperatures;
	
	public TemperaturePayload2Data() {
		super();
	}
	public TemperaturePayload2Data(String day, List<TemperaturePayload2DataData> temperatures) {
		this.day = day;
		this.temperatures = temperatures;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<TemperaturePayload2DataData> getTemperatures() {
		return temperatures;
	}
	public void setTemperatures(List<TemperaturePayload2DataData> temperatures) {
		this.temperatures = temperatures;
	}

	
}
