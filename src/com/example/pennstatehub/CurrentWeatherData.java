package com.example.pennstatehub;

public class CurrentWeatherData {

	private final String city;
	private final byte[] condIcon;
	private final String condDesc;
	private final String temp;
	private final String press;
	private final String hum;
	private final String windSpeed;
	private final String windDeg;
	
	public CurrentWeatherData(String city, byte[] condIcon, String condDesc, String temp, String press, String hum, String windSpeed, String windDeg)
	{
		this.city=city;
		this.condIcon=condIcon;
		this.condDesc=condDesc;
		this.temp=temp;
		this.press=press;
		this.hum=hum;
		this.windSpeed=windSpeed;
		this.windDeg=windDeg;
	}
	
	public String getCity(){
		return city;
	}
	
	public byte[] getCondIcon(){
		return condIcon;
	}
	
	public String getCondDesc(){
		return condDesc;
	}
	
	public String getTemp(){
		return temp;
	}
	
	public String getPress(){
		return press;
	}
	
	public String getHum(){
		return hum;
	}
	
	public String getWindSpeed(){
		return windSpeed;
	}
	
	public String getWindDeg(){
		return windDeg;
	}
}
