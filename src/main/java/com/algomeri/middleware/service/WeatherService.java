package com.algomeri.middleware.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algomeri.middleware.dto.CustomListResponse;
import com.algomeri.middleware.dto.CustomResponse;
import com.algomeri.middleware.dto.NextDayResponse;
import com.algomeri.middleware.dto.TemperaturePayload;
import com.algomeri.middleware.dto.TemperaturePayloadData;
import com.algomeri.middleware.external.WeatherDTO;

@Service
public class WeatherService {

	@Autowired
	CustomRequests requests;
	@Autowired
	Utils utils;
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public CustomResponse<TemperaturePayload> getTemp(String cityId) {
		try {
			
			// The temperature payload to be returned inside the custom response
			TemperaturePayload temperaturePayload = new TemperaturePayload();
			WeatherDTO weather = requests.fetch5DaysWeather(cityId);
			
			// Set the city, country and location id from weather dto
			temperaturePayload.setCity(weather.getCity().getName());
			temperaturePayload.setCountry(weather.getCity().getCountry());
			temperaturePayload.setLocationId(weather.getCity().getId());
			
			// set temperatures field in temperature payload and return as list after retrieving list filed from weather response 
			List<TemperaturePayloadData> temperatures = weather.getList().stream()
														.distinct().collect(Collectors.toList()).stream()
														.map(t -> new TemperaturePayloadData(new Date(t.getDt() * 1000L), t.getMain().getTemp()))
														.collect(Collectors.toList());

			temperaturePayload.setTemperatures(temperatures);

			return new CustomResponse<TemperaturePayload>(true, "temperatures retrieved", temperaturePayload);
		} catch (Exception e) {
			log.error("Error in getTemp method: " + e.getMessage());
			return new CustomResponse<>(false, "error");
		}
	}
	
	/**
	 * This method retrieves list of temperatures for the next day for a single location
	 * @param cityId
	 * @return {@link NextDayResponse}
	 */
	public NextDayResponse getNextDayTemperature(String cityId) {
		try {
			NextDayResponse res = new NextDayResponse();
			WeatherDTO weather = requests.fetch5DaysWeather(cityId);
			res.setCity(weather.getCity().getName());
			res.setCityId(weather.getCity().getId());
			
			List<TemperaturePayloadData> list = weather.getList()
					.stream()
					.filter(x -> convertToLocalDateTime(new Date(x.getDt() * 1000L)).getDayOfWeek() == LocalDateTime.now().plusDays(1).getDayOfWeek())
					.collect(Collectors.toList())
					.stream()
					.map(y -> new TemperaturePayloadData(new Date(y.getDt() * 1000L), y.getMain().getTemp()))
					.collect(Collectors.toList());
			
			res.setTemperatures(list);
			System.out.printf("Completed %s at %s\n", cityId, new Date().toString());
			return res;
		} catch (Exception e) {
			log.error("get next day temp error: " + e.getMessage());
			return null;
		}
	}

	
	/**
	 * This method retrieves List of temperatures for a list of locations 
	 * @param units
	 * @param cityIds
	 * @return {@link CustomListResponse<NextDayResponse>}
	 */
	public CustomListResponse<NextDayResponse> getNextDayTempForFavCities(String units, List<String> cityIds) {
		try {
			
			// add next day temp to response
			List<NextDayResponse> res = cityIds.stream().map(this::getNextDayTemperature).collect(Collectors.toList());
			
			if(units.toLowerCase().trim().equals("fahrenheit")) {
				res.stream().forEach(x -> x.getTemperatures().stream().forEach(y -> y.setTemperature(celsiusToFahrenheit(y.getTemperature())) ));
			}
			
			return new CustomListResponse<>(true, "success", res);
		} 
		catch (Exception e) {
			log.error("error getting nextday temp for fave cities because: " + e.getMessage());
			return new CustomListResponse<>(true, "error");
		}
	}
	


	public double kelvinToCelsius(double kelvin) {
		return kelvin - 273.15;
	}

	public double kelvinToFahrenheit(double kelvin) {
		return (kelvin - 273.15) * 9 / 5 + 32;
	}

	public double celsiusToFahrenheit(double celsius) {
		return (celsius * 9 / 5) + 32;
	}

	public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
