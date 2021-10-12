package com.algomeri.middleware.dto;

import java.util.List;

public class CustomListResponse <T> {

	private boolean status;
	private String message;
	private List<T> data;
	
	public CustomListResponse(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public CustomListResponse(boolean status, String message, List<T> data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public CustomListResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
