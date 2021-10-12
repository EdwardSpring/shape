package com.algomeri.middleware.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Utils {
	
	/**
	 * Method for turning a string into a date object
	 * @param dateString use this format: "2021-12-23 12:45 PM"
	 * @return {@link Date}
	 * @throws ParseException
	 */
	public Date parseDate(String dateString) {
		try {
			return new SimpleDateFormat("yyyy-M-dd hh:mm a").parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

}
