package com.algomeri.middleware.external;

import java.util.List;

public class WeatherDTO {
	private String cod;
	private String message;
	private long cnt;
	private List<Data1> list;
	private City city;
	
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getCnt() {
		return cnt;
	}
	public void setCnt(long cnt) {
		this.cnt = cnt;
	}
	public List<Data1> getList() {
		return list;
	}
	public void setList(List<Data1> list) {
		this.list = list;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	
	
	

}
