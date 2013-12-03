package com.example.pennstatehub;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class ForecastParser {

	private final String ns = null;

	public List<ForecastData> parse(InputStream inputStream) throws XmlPullParserException, IOException {
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
	
	private List<ForecastData> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		String highTemp=null;
		String lowTemp=null;
		String weatherDesc=null;
		Date date=null;
		List<ForecastData> items = new ArrayList<ForecastData>();
		
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if(parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if(name.equals("time")) {
				date = readDate(parser);
			}
			if(name.equals("temperature")) {
				highTemp = readHighTemp(parser);
				lowTemp = readLowTemp(parser);
			}
			if(name.equals("symbol")) {
				weatherDesc=readWeatherDesc(parser);
			}
			if(highTemp!=null&&lowTemp!=null&&weatherDesc!=null&&date!=null) {
				ForecastData data = new ForecastData(highTemp,lowTemp,weatherDesc,date);
				items.add(data);
				date=null;
				highTemp=null;
				lowTemp=null;
				date=null;
			}
		}
		return items;
	}
	
	private Date readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
		String str=parser.getAttributeValue(ns, "day");
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		Log.w("datehelp", str);
		try{
			date=df.parse(str);
		}
		catch(Exception e) {}
		finally {}
		return date;
	}
	
	private String readHighTemp(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "max");
	}
	
	private String readLowTemp(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "min");
	}
	
	private String readWeatherDesc(XmlPullParser parser) throws IOException, XmlPullParserException {
		return parser.getAttributeValue(ns, "name");
	}
}