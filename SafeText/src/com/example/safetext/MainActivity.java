package com.example.safetext;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String LOCATION = "com.example.safetext.LOCATION";
	private String _address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setLocation(View view) {
		EditText editText = (EditText) findViewById(R.id.edit_message);
		_address = editText.getText().toString();
		Intent geocoding_intent = new Intent(this, GeocodingActivity.class);
		geocoding_intent.putExtra(LOCATION, _address);
		startActivity(geocoding_intent);
	}

}
