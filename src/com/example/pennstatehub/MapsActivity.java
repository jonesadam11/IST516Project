package com.example.pennstatehub;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;


public class MapsActivity extends Activity implements OnInfoWindowClickListener {

	private GoogleMap mMap;
	
	private static final LatLng UP = new LatLng (40.80, -77.86);
	//private static final LatLngBounds UPBounds = new LatLngBounds(new LatLng(40.78, -77.87), new LatLng(40.81, -77.86));
	private static final int UPZoom=15;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		// Show the Up button in the action bar.
		setupActionBar();		
		setUpMapIfNeeded(UP, UPZoom);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setUpMapIfNeeded(LatLng startLocation, int zoom) {
		if (mMap==null){
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
		}
		if (mMap != null) {
			CameraPosition cameraPosition = new CameraPosition.Builder().target(
	                startLocation).zoom(zoom).build();	 
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			mMap.setMyLocationEnabled(true);
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(40.793744,-77.868130))
				.title("IST Building")
				.snippet("Get Walking Directions"));
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(40.798314,-77.861674))
				.title("The HUB")
				.snippet("Get Walking Directions"));
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(40.803514,-77.862465))
				.title("The Creamery")
				.snippet("Get Walking Directions"));
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(40.802275,-77.860883))
				.title("Eisenhower Parking Garage")
				.snippet("Get Walking Directions"));
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(40.802986,-77.860038))
				.title("Student Health Center")
				.snippet("Get Walking Directions"));
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(40.806991,-77.858598))
				.title("Shields Building")
				.snippet("Get Walking Directions"));
			mMap.setOnInfoWindowClickListener(this);
		}
	}


	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		String url = "http://maps.google.com/maps?daddr="+marker.getPosition().latitude+","+marker.getPosition().longitude+"&dirflg=w";
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		startActivity(intent);
	}
	
	

}
