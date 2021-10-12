package com.algomeri.middleware.controllers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algomeri.middleware.dto.CustomResponse;
import com.algomeri.middleware.dto.TemperaturePayload;
import com.algomeri.middleware.service.CustomRequests;
import com.algomeri.middleware.service.WeatherService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RequestMapping("internal/weather")
@RestController
public class Weather {
	
	private final Bucket bucket;
	private WeatherService service;
	private CacheManager cacheManager;
	@Autowired CustomRequests requests;
	
	public Weather(WeatherService service, CacheManager cacheManager) {
		this.service = service;
		this.cacheManager = cacheManager;

		// create call limit
		Bandwidth limit = Bandwidth.classic(10000, Refill.intervally(10000, Duration.ofHours(24)));

		// add limit to bucket
		this.bucket = Bucket4j.builder().addLimit(limit).build();
	}
	
	@GetMapping("/1/5-day-temperature/{cityId}")
	public ResponseEntity<CustomResponse<TemperaturePayload>> get5DayTemperature(@PathVariable("cityId") String cityId) {

		// If city has not been cached
		if (!containsKey(cityId)) {
			
			// check if a call can still be made
			if (bucket.tryConsume(1)) {

				// make the call and return temperature
				return ResponseEntity.ok(service.getTemp(cityId));
			}
			else {
				return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new CustomResponse<>(false, "unable to make call"));
			}
			
		}

		// If city has been previously cached
		else {
			// just get it from cache
			return ResponseEntity.ok(service.getTemp(cityId));
		}
	}
	
	@GetMapping("/temp/next-day/{cityId}")
	public Object getNextDayTemperature(@PathVariable("cityId") String cityId) {

		// If city has not been cached
		if (!containsKey(cityId)) {
			
			// check if a call can still be made
			if (bucket.tryConsume(1)) {

				// make the call and return temperature
				return ResponseEntity.ok(service.getNextDayTemperature(cityId));
			}
			return null;
		}

		// If city has been previously cached
		else {
			// just get it from cache
			return ResponseEntity.ok(service.getNextDayTemperature(cityId));
		}
	}
	
	
	@GetMapping("summary")
	public Object getNextDayTempForLocations(@RequestParam("units") String units, @RequestParam("cityIds") String cityIds) {
		
		if (cityIds.isBlank() || cityIds == null) {
			return ResponseEntity.status(422).body(new CustomResponse<>(false, "unable to make call"));
		}
		
		
		// Add an extra comma incase initially there's no comma to prevent error at splitting
		cityIds.concat(",");

		// Create list to hold split cities
		List<String> cities = Arrays.asList(cityIds.replaceAll("\s", "").split(","));
		
		// If all cities requested have been cached
		if (cities.stream().allMatch(this::containsKey)) {
			return ResponseEntity.ok(service.getNextDayTempForFavCities(units, cities));
		}

		// If any city in the request has not been cached yet
		else if (cities.stream().anyMatch(x -> !containsKey(x))) {

			// retrieve the cached and uncached cities
			List<String> uncached = cities.stream().filter(x -> !containsKey(x)).collect(Collectors.toList());
			List<String> cached = cities.stream().filter(this::containsKey).collect(Collectors.toList());
			
			System.out.println("Uncached: " + uncached);
			System.out.println("Cached: " + cached);

			// If all tokens have been used
			if (getAvailableTokens() == 0) {

				// process and send response for only the cached cities in list
				return ResponseEntity.ok(service.getNextDayTempForFavCities(units, cached));
			}
			
			// If tokens left is sufficient for uncached requests
			else if (getAvailableTokens() >= uncached.size()) {
				if (bucket.tryConsume(uncached.size())) {
					
					// process initial cities in request
					return ResponseEntity.ok(service.getNextDayTempForFavCities(units, cities));
				}
			}
			// If tokens left is less than number of cities requested
			else {

				// remove the extra cities that can't make the call and use up the rest
				int itemsToRemove = (int) (uncached.size() - getAvailableTokens());
				int g = 0;
				while (g < itemsToRemove) {
					uncached.remove(g);
					g++;
				}

				cached.addAll(uncached);
				if (bucket.tryConsume(getAvailableTokens())) {
					ResponseEntity.ok(service.getNextDayTempForFavCities(units, cached));
				}
			}

		}
			
		
		// lastly
		return new CustomResponse<>(false, "unable to make call");
		
	}
	
	/*
	
	@GetMapping("summary")
	public Object getNextDayTempForLocations(@RequestParam("units") String units, @RequestParam("cityIds") String cityIds) {
		if (cityIds != null) {
			
			String[] cities;
			
			// If no comma found in String, just create a single array with that current value
			if(!cityIds.contains(",")) {
				cities = new String[] {cityIds};
			}
			cities = cityIds.replaceAll("\s", "").split(",");
			
			
			// If all cities requested have been cached
			if (Arrays.stream(cities).allMatch(this::containsKey)) {
				return  ResponseEntity.ok(service.getNextDayTempForFavCitiesAlreadyCached(units, cities));
			}
			
			// If any city in the request has not been cached yet
			else if (Arrays.stream(cities).anyMatch(x -> !containsKey(x))) {
				
				// retrieve the cached and uncached cities
				int i = 0;
				List<String> uncached = new ArrayList<String>();
				List<String> cached = new ArrayList<String>();
				while (i < cities.length) {
					if (!containsKey(cities[i])) {
						uncached.add(cities[i]);
					}
					else {
						cached.add(cities[i]);
					}
					i++;
				}
				
				// If all tokens have been used
				if (getAvailableTokens() == 0) {
					
					// send response for only the cached cities in list
					String[] arr = new String[cached.size()];
					cached.toArray(arr);
					return ResponseEntity.ok(service.getNextDayTempForFavCitiesAlreadyCached(units, arr));
				}
				// If tokens left is sufficient for uncached requests
				else if (getAvailableTokens() >= uncached.size()) {
					if (bucket.tryConsume(uncached.size())) {
						return ResponseEntity.ok(service.getNextDayTempForFavCitiesAlreadyCached(units, cities));
					}
				}
				// If tokens left is less than number of cities requested
				else {
					
					// remove the extra cities that can't make the call and use up the rest
					int itemsToRemove = (int) (uncached.size() - getAvailableTokens());
					int g = 0;
					while (g < itemsToRemove) {
						uncached.remove(g);
						g++;
					}
					
					cached.addAll(uncached);
					String[] arr = new String[cached.size()];
					cached.toArray(arr);
					
					if (bucket.tryConsume(getAvailableTokens())) {
						ResponseEntity.ok(service.getNextDayTempForFavCitiesAlreadyCached(units, arr));
					}
				}
				
			}
			
			
		}
		
		// lastly
		return new CustomResponse<>(false, "unable to make call");
		
	}
	
	*/
	
	@GetMapping("/{cityId}")
	public ResponseEntity<Object> get5DayWeather(@PathVariable("cityId") String cityId) {
		// If city has not been cached
		if (!containsKey(cityId)) {

			// check if a call can still be made
			if (bucket.tryConsume(1)) {

				// make the call and return temperature
				return ResponseEntity.ok(requests.fetch5DaysWeather(cityId));
			}
			return null;
		}

		// If city has been previously cached
		else {
			// just get it from cache
			return ResponseEntity.ok(requests.fetch5DaysWeather(cityId));
		}
	}
	
	
	@GetMapping("/calls-left")
	public long getAvailableTokens() {
		return bucket.getAvailableTokens();
	}
	
	@GetMapping("/cache")
	public Object getCache() {
		return cacheManager.getCache("weather").getNativeCache();
	}
	
	@GetMapping("/contains/{key}")
	public boolean containsKey(@PathVariable String key) {
		Cache cache = cacheManager.getCache("weather");
		return cache.get(key) != null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	@GetMapping("")
//	public  ResponseEntity<Object> getWeather(@RequestParam("unit") String unit, @RequestParam("locations") String locations) {
//		return service.getWeather(unit, locations);
//	}
	
//	@GetMapping("locations/{cityId}")
//	public  ResponseEntity<Object> getWeatherByLocation(@PathVariable("cityId") String cityId) {
//		return service.fetch5DaysWeather(cityId);
//	}
	
//	@GetMapping("/temp")
//	public Object get5DayTemperatures() {
//		return service.get5DayTemperatures();
//	}
	
	
	

}
