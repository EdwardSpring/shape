package com.algomeri.middleware.dto;

public class NativeCache <T>{
	private CacheData<T> key; // the cache key

	public CacheData<T> getKey() {
		return key;
	}

	public void setKey(CacheData<T> key) {
		this.key = key;
	}

	public class CacheData <O>{
		private Object headers;
		private O body;
		private String statusCode;
		private int statusCodeValue;

		public Object getHeaders() {
			return headers;
		}

		public void setHeaders(Object headers) {
			this.headers = headers;
		}

		public O getBody() {
			return body;
		}

		public void setBody(O body) {
			this.body = body;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public int getStatusCodeValue() {
			return statusCodeValue;
		}

		public void setStatusCodeValue(int statusCodeValue) {
			this.statusCodeValue = statusCodeValue;
		}
		
		

	}

}
