package com.example.pennstatehub;

import java.util.Calendar;
import java.util.Date;

public class ForecastData {
	
	private final String highTemp;
	private final String lowTemp;
	private final String weatherDesc;
	private final Date date;
	
	public ForecastData(String highTemp, String lowTemp, String weatherDesc, Date date) {
		this.highTemp=highTemp;
		this.lowTemp=lowTemp;
		this.weatherDesc=weatherDesc;
		this.date=date;
	}
	
	public String getHighTemp() {
		return highTemp;
	}
	
	public String getLowTemp() {
		return lowTemp;
	}
	
	public String getWeatherDesc() {
		return weatherDesc;
	}
	
	public Date getDate() {
		return date;
	}

}
