package com.algomeri.middleware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.algomeri.middleware.external.WeatherDTO;

@Service
public class CustomRequests {

	private CustomHttp http;
	Logger log = LoggerFactory.getLogger(this.getClass());
	private String appId = "489ff05990a3f9ddfb20628429158fa7";
	
	public CustomRequests(CustomHttp http) {
		this.http = http;
	}


	/**
	 * This method is used to fetch weather for 5 days.<br>
	 * i.e it calls the customHttp method fetch. <br>
	 * The call only gets made if the city has not been cached. <br>
	 * If it has been cached, it just returns the data for the cached city. <br>
	 * The cache although gets cleared after 24 hours. Check the schedules package
	 * @param cityId
	 * @return {@link WeatherDTO}
	 */
	@Cacheable(value = "weather", key = "#cityId", unless = "#result == null")
	public WeatherDTO fetch5DaysWeather(String cityId){
		try {
			String url = String.format("https://api.openweathermap.org/data/2.5/forecast?id=%s&appid=%s&units=metric", cityId, appId);
			WeatherDTO res = http.fetch(url, WeatherDTO.class);
			return res;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
