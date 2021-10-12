package com.algomeri.middleware.dto;

import java.util.Date;

public class TemperaturePayloadData {
	private Date date;
	private double temperature;

	public TemperaturePayloadData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TemperaturePayloadData(Date date, double temperature) {
		super();
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