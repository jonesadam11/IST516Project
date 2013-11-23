package com.example.pennstatehub;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class RssService extends IntentService {
	
	//public static final String RSS_LINK = "http://news.psu.edu/rss/audience/students";
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	
	public RssService() {
		super("RssService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		String link = intent.getExtras().getString("rsslink");
		List<RssItem> rssItems = null;
		try{
			RssParser parser = new RssParser();
			rssItems=parser.parse(getInputStream(link));
		}
		catch (XmlPullParserException e) {
        } 
		catch (IOException e) {
        }
		Bundle bundle = new Bundle();
		bundle.putSerializable(ITEMS, (Serializable)rssItems);
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
