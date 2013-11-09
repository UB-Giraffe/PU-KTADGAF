package com.example.safetext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.util.List;
import com.google.android.gms.maps.GoogleMap;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeocodingActivity extends Activity {
	
	private double _lat;
	private double _lng;
	private GoogleMap _mMap;
	private String _phoneNumber;
	private String _messageText;
	
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
        
		_mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//		mMap.setMyLocationEnabled(true);
		
		
		Geocoder geocoder = new Geocoder(this);
		
		try {
			List<Address> list_addresses = geocoder.getFromLocationName(address, 3);
			_lat = list_addresses.iterator().next().getLatitude();
			_lng = list_addresses.iterator().next().getLongitude();
			_mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(_lat, _lng))
	        .title("SendText"));
			_mMap.setMyLocationEnabled(true);
			
			LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locManager.requestLocationUpdates(locManager.GPS_PROVIDER, 2, 5, new LocationListener(){

				@Override
				public void onLocationChanged(Location location) {
					double myLat = location.getLatitude(); //location we're at
					double myLong = location.getLongitude(); //location we're at
					if((myLat > _lat - 0.003618) && (myLat < _lat + 0.003618)){
						if((myLong > _lng - 0.003618) && (myLong < _lng + 0.003618)){
							sendSMS();
						}
					}
					
				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
				
			});

	
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
	
	public void sms() {
		EditText one = (EditText) findViewById(R.id.editText1);
		EditText two = (EditText) findViewById(R.id.editText2);
		_phoneNumber = one.getText().toString();
		_messageText = two.getText().toString();
	}
	
	public void sendSMS(){
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(_phoneNumber, null, _messageText, null,
					null);
//			Toast.makeText(getApplicationContext(), "SMS Sent!",Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), _phoneNumber,Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	}
	

}
