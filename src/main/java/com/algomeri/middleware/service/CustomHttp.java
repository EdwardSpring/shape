package com.algomeri.middleware.service;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.algomeri.middleware.dto.CustomResponse;
import com.algomeri.middleware.external.WeatherDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomHttp {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public CustomResponse<WeatherDTO> fetch(String url) {
		try {
			HttpGet request = new HttpGet(url);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(request);
			
			if(response.getStatusLine().getStatusCode() != 200) return new CustomResponse<>(false, "error");
			
			HttpEntity entity = response.getEntity();
			WeatherDTO dto = new ObjectMapper().readValue(entity.getContent(), WeatherDTO.class);
			return new CustomResponse<WeatherDTO>(true, "weather details retrived", dto);
		} 
		catch (Exception e) {
			log.error("Could not fetch 5 day weather because: " + e.getMessage());
			return new CustomResponse<>(false, "error");
		}
	}
	
	public <T> T fetch(String url, Class<T> dtoClass) {
		try {
			HttpGet request = new HttpGet(url);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(request);
			
			if(response.getStatusLine().getStatusCode() != 200) return null ;
			
			HttpEntity entity = response.getEntity();
			T dto = new ObjectMapper().readValue(entity.getContent(), dtoClass);
			
			return dto;
		} 
		catch (Exception e) {
			log.error("Could not fetch 5 day weather because: " + e.getMessage());
			return null;
		}
	}

}
