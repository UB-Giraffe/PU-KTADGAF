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
	private String _phoneNumber;
	private String _messageText;
	

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
	public void setLocation(View view){
		EditText editText = (EditText) findViewById(R.id.edit_message);
		_address = editText.getText().toString();
		setContentView(R.layout.activity_texting);
	}
	public void geolocation(){
		Intent geocoding_intent = new Intent(this, GeocodingActivity.class);
		geocoding_intent.putExtra(LOCATION, _address);
		startActivity(geocoding_intent);
	}
	public void sms(View view){
		_phoneNumber = findViewById(R.id.editText1).toString();
		_messageText = findViewById(R.id.editText2).toString();

		  try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(_phoneNumber, null, _messageText, null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent!",
						Toast.LENGTH_LONG).show();
		  } catch (Exception e) {
			Toast.makeText(getApplicationContext(),
				"SMS faild, please try again later!",
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		  }
		geolocation();
	}

}