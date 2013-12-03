package com.example.pennstatehub;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentWeatherFragment extends Fragment {

	private ProgressBar progressBar;
	private TextView cityText;
	private ImageView condIcon;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView hum;
	private TextView windSpeed;
	private TextView windDeg;
	private View view;
	private TextView forecast1Lab;
	private TextView forecast2Lab;
	private TextView forecast3Lab;
	private TextView forecast4Lab;
	private TextView forecast5Lab;
	private TextView forecast1;
	private TextView forecast2;
	private TextView forecast3;
	private TextView forecast4;
	private TextView forecast5;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		if (view==null){
			view=inflater.inflate(R.layout.weather_layout, container, false);
			progressBar = (ProgressBar) view.findViewById(R.id.weatherProgressBar);
			cityText=(TextView)view.findViewById(R.id.cityText);
			condIcon=(ImageView)view.findViewById(R.id.condIcon);
			condDescr=(TextView)view.findViewById(R.id.condDescr);
			temp=(TextView)view.findViewById(R.id.temp);
			press=(TextView)view.findViewById(R.id.press);
			hum=(TextView)view.findViewById(R.id.hum);
			windSpeed=(TextView)view.findViewById(R.id.windSpeed);
			windDeg=(TextView)view.findViewById(R.id.windDeg);
			forecast1Lab=(TextView)view.findViewById(R.id.forecast1Lab);
			forecast2Lab=(TextView)view.findViewById(R.id.forecast2Lab);
			forecast3Lab=(TextView)view.findViewById(R.id.forecast3Lab);
			forecast4Lab=(TextView)view.findViewById(R.id.forecast4Lab);
			forecast5Lab=(TextView)view.findViewById(R.id.forecast5Lab);
			forecast1=(TextView)view.findViewById(R.id.forecast1Cont);
			forecast2=(TextView)view.findViewById(R.id.forecast2Cont);
			forecast3=(TextView)view.findViewById(R.id.forecast3Cont);
			forecast4=(TextView)view.findViewById(R.id.forecast4Cont);
			forecast5=(TextView)view.findViewById(R.id.forecast5Cont);			
			startService();
		}
		else {
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		return view;
	}
	
	public void startService() {
		Intent intent = new Intent (getActivity(), CurrentWeatherService.class);
		intent.putExtra(CurrentWeatherService.RECEIVER, resultReceiver);
		getActivity().startService(intent);
		Intent forecast = new Intent (getActivity(), ForecastService.class);
		forecast.putExtra(ForecastService.RECEIVER, forecastReceiver);
		getActivity().startService(forecast);
	}
	
	private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			List<CurrentWeatherData> items =(List<CurrentWeatherData>)resultData.getSerializable(CurrentWeatherService.WEATHER_DATA);
			CurrentWeatherData data = items.get(0);
			if(data != null) {
				cityText.setText(capitolizeString(data.getCity()));
				condIcon.setImageBitmap(BitmapFactory.decodeByteArray(data.getCondIcon(), 0, data.getCondIcon().length));
				condDescr.setText(capitolizeString(data.getCondDesc()));
				temp.setText(data.getTemp());
				press.setText(data.getPress());
				hum.setText(data.getHum());
				windSpeed.setText(data.getWindSpeed());
				windDeg.setText(capitolizeString(data.getWindDeg()));
			}
			else {
				Toast.makeText(getActivity(), "An error has occured with the weather feed.", Toast.LENGTH_LONG).show();
			}
			progressBar.setVisibility(View.GONE);
		};
	};
	
	private final ResultReceiver forecastReceiver = new ResultReceiver(new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			List<ForecastData> items =(List<ForecastData>)resultData.getSerializable(ForecastService.WEATHER_DATA);
			List<String> daysOfWeek=new ArrayList<String>();
			daysOfWeek.add("Sunday");
			daysOfWeek.add("Monday");
			daysOfWeek.add("Tuesday");
			daysOfWeek.add("Wednesday");
			daysOfWeek.add("Thursday");
			daysOfWeek.add("Friday");
			daysOfWeek.add("Saturday");
			if(items != null) {
				ForecastData fd1=items.get(0);
				ForecastData fd2=items.get(1);
				ForecastData fd3=items.get(2);
				ForecastData fd4=items.get(3);
				ForecastData fd5=items.get(4);
				
				forecast1Lab.setText(daysOfWeek.get(fd1.getDate().getDay()));
				forecast2Lab.setText(daysOfWeek.get(fd2.getDate().getDay()));
				forecast3Lab.setText(daysOfWeek.get(fd3.getDate().getDay()));
				forecast4Lab.setText(daysOfWeek.get(fd4.getDate().getDay()));
				forecast5Lab.setText(daysOfWeek.get(fd5.getDate().getDay()));
				forecast1.setText(capitolizeString(fd1.getWeatherDesc())+" High:" + fd1.getHighTemp() + " Low:" + fd1.getLowTemp());
				forecast2.setText(capitolizeString(fd2.getWeatherDesc())+" High:" + fd2.getHighTemp() + " Low:" + fd2.getLowTemp());
				forecast3.setText(capitolizeString(fd3.getWeatherDesc())+" High:" + fd3.getHighTemp() + " Low:" + fd3.getLowTemp());
				forecast4.setText(capitolizeString(fd4.getWeatherDesc())+" High:" + fd4.getHighTemp() + " Low:" + fd4.getLowTemp());
				forecast5.setText(capitolizeString(fd5.getWeatherDesc())+" High:" + fd5.getHighTemp() + " Low:" + fd5.getLowTemp());
			}
			else {
				Toast.makeText(getActivity(), "An error has occured with the weather feed.", Toast.LENGTH_LONG).show();
			}
			progressBar.setVisibility(View.GONE);
		};
	};
	
	private String capitolizeString(String str)
	{
		final StringBuilder result = new StringBuilder(str.length());
		String[] words = str.split("\\s");
		for(int i=0,l=words.length;i<l;++i) {
		  if(i>0) result.append(" ");      
		  result.append(Character.toUpperCase(words[i].charAt(0)))
		        .append(words[i].substring(1));

		}
		return result.toString();
	}
}
