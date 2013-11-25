package com.example.pennstatehub;

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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		if (view==null){
			view=inflater.inflate(R.layout.weather_layout, container, false);
			progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			cityText=(TextView)view.findViewById(R.id.cityText);
			condIcon=(ImageView)view.findViewById(R.id.condIcon);
			condDescr=(TextView)view.findViewById(R.id.condDescr);
			temp=(TextView)view.findViewById(R.id.temp);
			press=(TextView)view.findViewById(R.id.press);
			hum=(TextView)view.findViewById(R.id.hum);
			windSpeed=(TextView)view.findViewById(R.id.windSpeed);
			windDeg=(TextView)view.findViewById(R.id.windDeg);
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
	}
	
	private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			CurrentWeatherData data=(CurrentWeatherData)resultData.getSerializable(CurrentWeatherService.WEATHER_DATA);
			//List<RssItem> items =(List<RssItem>)resultData.getSerializable(RssService.ITEMS);
			if(data != null) {
				cityText.setText(data.getCity());
				condIcon.setImageBitmap(BitmapFactory.decodeByteArray(data.getCondIcon(), 0, data.getCondIcon().length));
				condDescr.setText(data.getCondDesc());
				temp.setText(data.getTemp());
				press.setText(data.getPress());
				hum.setText(data.getHum());
				windSpeed.setText(data.getWindSpeed());
				windDeg.setText(data.getWindDeg());
			}
			else {
				Toast.makeText(getActivity(), "An error has occured with the weather feed.", Toast.LENGTH_LONG).show();
			}
			progressBar.setVisibility(View.GONE);
		};
	};
}
