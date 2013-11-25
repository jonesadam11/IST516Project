package com.example.pennstatehub;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class CurrentWeatherService extends IntentService {

	public static final String WEATHER_DATA = "data";
	public static final String RECEIVER = "receiver";
	
	public CurrentWeatherService() {
		super("CurrentWeatherService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String link="http://api.openweathermap.org/data/2.5/weather?lat=40.80&lon=-77.86&mode=xml&units=imperial";
		CurrentWeatherData data = null;
		try {
			CurrentWeatherParser parser = new CurrentWeatherParser();
			data=parser.parse(getInputStream(link));
		}
		catch (XmlPullParserException e) {
        } 
		catch (IOException e) {
        }
		Bundle bundle = new Bundle();
		bundle.putSerializable(WEATHER_DATA, (Serializable)data);
		ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
		receiver.send(0, bundle);
	}
	
	public InputStream getInputStream(String link) {
		try{
			URL url=new URL(link);
			return url.openConnection().getInputStream();
		}
		catch (IOException e) {
			return null;
		}
	}

}
