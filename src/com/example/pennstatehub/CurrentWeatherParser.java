package com.example.pennstatehub;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class CurrentWeatherParser {
	
	private final String ns = null;
	
	public CurrentWeatherData parse(InputStream inputStream) throws XmlPullParserException, IOException {
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputStream, null);
			parser.nextTag();
			return readFeed(parser);
		}
		finally {
			inputStream.close();
		}
	}
	
	private CurrentWeatherData readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		//parser.require(XmlPullParser.START_TAG, null, "rss");
		String city=null;
		byte[] condIcon=null;
		String condDesc=null;
		String temp=null;
		String press=null;
		String hum=null;
		String windSpeed=null;
		String windDeg=null;
		CurrentWeatherData data = null;
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if(parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if(name.equals("city")) {
				city = readCity(parser);
			}
			else if (name.equals("temperature")) {
				temp = readTemperature(parser);
			}
			else if (name.equals("humidity")) {
				hum = readHumidity(parser);
			}
			else if (name.equals("pressure")) {
				press = readPressure(parser);
			}
			else if (name.equals("speed")) {
				windSpeed=readWindSpeed(parser);
			}
			else if (name.equals("direction")) {
				windDeg=readWindDeg(parser);
			}
			else if (name.equals("weather")) {
				condDesc=readCondDesc(parser);
				condIcon=readCondIcon(parser);
			}
		}
		if (city != null && temp != null && hum != null && press != null && windSpeed != null && windDeg != null && condDesc != null && condIcon != null) {
			data = new CurrentWeatherData(city, condIcon, condDesc, temp, press, hum, windSpeed, windDeg);
		}
		return data;
	}

}
