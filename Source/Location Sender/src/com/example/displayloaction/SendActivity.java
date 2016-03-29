package com.example.displayloaction;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener {

	 ProgressDialog pDialog;
	 double lat;
	    double lng;
    int flag=0;
    String callerno;
    EditText latedit,lngedit,addedit;
    
    LocationClient x;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		
		Bundle extras = getIntent().getExtras();
        flag=extras.getInt("flag1");
        callerno=extras.getString("phonenum");
        
        latedit=(EditText) findViewById(R.id.latet);
        lngedit=(EditText) findViewById(R.id.lnget);
        addedit=(EditText) findViewById(R.id.addet);
        
        x= new LocationClient(this,this,this);
        
        x.connect();
        
       Toast.makeText(this,flag+"  "+callerno,Toast.LENGTH_SHORT).show();
      
       
       
	}
	
	
	
	
	

	class LoadAllProducts extends AsyncTask<String, String, String> {

		
		 JSONParser  jParser =  new JSONParser();
			
		 JSONObject json;
		 String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SendActivity.this);
			pDialog.setMessage("Loading Details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
			 
			 Log.e("dasfs", "dsfdsfsdf344444s");
			 params.add(new BasicNameValuePair("latlng",lat+","+lng));
			 params.add(new BasicNameValuePair("sensor","false"));
			 json = jParser.makeHttpRequest(get_latlng, "GET", params);
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			/*if(json!=null){
				try {
					String name = json.getString("name");
					Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
			if(json!=null){
				//Toast.makeText(getApplicationContext(), jsondir.toString(), Toast.LENGTH_LONG).show();
				try {
					JSONObject j1=json.getJSONArray("results").getJSONObject(0);
					String add=j1.getString("formatted_address");
					addedit.setText(add);
					
					//Toast.makeText(getApplicationContext(), "Your location offffff", Toast.LENGTH_SHORT).show();
		    	    
					
					
					sendSms(callerno, "Location of This Number is "+add);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			else{
				Toast.makeText(getApplicationContext(), "Error Error", Toast.LENGTH_SHORT).show();
			}
		}
		
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send, menu);
		return true;
	}
	
	void sendSms(String callernumber,String msg){

      	SmsManager smsManager = SmsManager.getDefault();
	    smsManager.sendTextMessage(callernumber, null, msg, null, null);
	    Toast.makeText(this,"SMS sent",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this,"failedconnected",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();
		Location l1=new Location(x.getLastLocation());
		lat=l1.getLatitude();
		lng=l1.getLongitude();
		latedit.setText(lat+"");
	    lngedit.setText(lng+"");
	  // getAddressString();
	   
	  new LoadAllProducts().execute();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Toast.makeText(this,"disconnected",Toast.LENGTH_SHORT).show();
	}

}
