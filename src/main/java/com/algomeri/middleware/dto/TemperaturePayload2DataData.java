package com.algomeri.middleware.dto;

import java.util.Date;

public class TemperaturePayload2DataData {
	private Date date;
	private double temperature;
	
	public TemperaturePayload2DataData() {
		super();
	}
	public TemperaturePayload2DataData(Date date, double temperature) {
		this.date = date;
		this.temperature = temperature;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	

}
