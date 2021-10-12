package com.algomeri.middleware.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Data1 {
	private long dt;
	private Main main;
	private int visibility;
	private int pop;
	private Sys sys;
	private String dt_txt;
	private List<Weather> weather;
	private Clouds clouds;
	private Wind wind;
	
	@JsonIgnoreProperties
	@JsonIgnore
	private Rain rain;
	@JsonIgnoreProperties
	@JsonIgnore	
	private Snow snow;
	
	public Rain getRain() {
		return rain;
	}

	public void setRain(Rain rain) {
		this.rain = rain;
	}

	public Snow getSnow() {
		return snow;
	}

	public void setSnow(Snow snow) {
		this.snow = snow;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public String getDt_txt() {
		return dt_txt;
	}

	public void setDt_txt(String dt_txt) {
		this.dt_txt = dt_txt;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public class Sys {
		private String pod;

		public String getPod() {
			return pod;
		}

		public void setPod(String pod) {
			this.pod = pod;
		}

	}

	public class Main {
		private double temp;
		private double feels_like;
		private double temp_min;
		private double temp_max;
		private int pressure;
		private int sea_level;
		private int grnd_level;
		private int humidity;
		private double temp_kf;

		public double getTemp() {
			return temp;
		}

		public void setTemp(double temp) {
			this.temp = temp;
		}

		public double getFeels_like() {
			return feels_like;
		}

		public void setFeels_like(double feels_like) {
			this.feels_like = feels_like;
		}

		public double getTemp_min() {
			return temp_min;
		}

		public void setTemp_min(double temp_min) {
			this.temp_min = temp_min;
		}

		public double getTemp_max() {
			return temp_max;
		}

		public void setTemp_max(double temp_max) {
			this.temp_max = temp_max;
		}

		public int getPressure() {
			return pressure;
		}

		public void setPressure(int pressure) {
			this.pressure = pressure;
		}

		public int getSea_level() {
			return sea_level;
		}

		public void setSea_level(int sea_level) {
			this.sea_level = sea_level;
		}

		public int getGrnd_level() {
			return grnd_level;
		}

		public void setGrnd_level(int grnd_level) {
			this.grnd_level = grnd_level;
		}

		public int getHumidity() {
			return humidity;
		}

		public void setHumidity(int humidity) {
			this.humidity = humidity;
		}

		public double getTemp_kf() {
			return temp_kf;
		}

		public void setTemp_kf(double temp_kf) {
			this.temp_kf = temp_kf;
		}

	}

}
