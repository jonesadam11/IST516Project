package com.example.pennstatehub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
			parser.next();
		}
		if (city != null && temp != null && hum != null && press != null && windSpeed != null && windDeg != null && condDesc != null && condIcon != null) {
			data = new CurrentWeatherData(city, condIcon, condDesc, temp, press, hum, windSpeed, windDeg);
		}
		return data;
	}
	
	private String readCity(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "name");
	}
	
	private String readTemperature(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "value") + " F";
	}
	
	private String readHumidity(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "value") + parser.getAttributeValue(ns, "unit");
	}
	
	private String readPressure(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "value") + " " + parser.getAttributeValue(ns, "unit");
	}
	
	private String readWindSpeed(XmlPullParser parser) throws IOException, XmlPullParserException {
		return  parser.getAttributeValue(ns, "name") + ": " + parser.getAttributeValue(ns, "value") + "mph";
	}
	
	private String readWindDeg(XmlPullParser parser) throws IOException, XmlPullParserException {
		return  parser.getAttributeValue(ns, "name");
	}
	
	private String readCondDesc(XmlPullParser parser) throws IOException, XmlPullParserException {
		return  parser.getAttributeValue(ns, "value");
	}
	
	private byte[] readCondIcon(XmlPullParser parser) throws IOException, XmlPullParserException {
		String baseURL="http://openweathermap.org/img/w/";
		String code=parser.getAttributeValue(ns, "icon");
        InputStream is = null;
        HttpGet httpReq = null;
        try {
        	URL url = new URL(baseURL+code+".png");
        	httpReq=new HttpGet(url.toURI());
        	HttpClient client=new DefaultHttpClient();
        	HttpResponse resp=(HttpResponse)client.execute(httpReq);
        	HttpEntity entity=resp.getEntity();
        	BufferedHttpEntity buf=new BufferedHttpEntity(entity);
        	is=buf.getContent();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ( is.read(buffer) != -1)
                baos.write(buffer);
            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
        }

        return null;

	}
}
