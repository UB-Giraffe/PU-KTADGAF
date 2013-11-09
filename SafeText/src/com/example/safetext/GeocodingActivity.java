package com.example.safetext;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.Geofence.Builder;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeocodingActivity extends Activity {
	
	private GoogleMap mMap;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_map);
		/**
		 * Set up the {@link android.app.ActionBar}.
		 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Get the message from the intent
        Intent intent = getIntent();
        String address = intent.getStringExtra(MainActivity.LOCATION);
        
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//		mMap.setMyLocationEnabled(true);
		
		Geocoder geocoder = new Geocoder(this);
		
		try {
			List<Address> list_addresses = geocoder.getFromLocationName(address, 3);
			double lat = list_addresses.iterator().next().getLatitude();
			double lng = list_addresses.iterator().next().getLongitude();
			mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(lat, lng))
	        .title("SendText"));
			mMap.setMyLocationEnabled(true);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TextView textview = new TextView(this);
		    textview.setTextSize(40);
		    textview.setText("ERROR");
			setContentView(textview);
			e.printStackTrace();
		}
		
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
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

}
