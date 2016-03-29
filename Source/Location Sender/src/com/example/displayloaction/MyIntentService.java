package com.example.displayloaction;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {

	JSONParser  jParser =  new JSONParser();
	
	 JSONObject json;
	 String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
	 String add;
	
	public MyIntentService() {
		super(MyIntentService.class.getSimpleName());
		Log.e("intent SERvvvvvvvvvvvv", "Suppppppppppppppppppprewe");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.e("intent SERvvvvvvvvvvvv", "hellow it s");
		Toast.makeText(getBaseContext(), "hellow", Toast.LENGTH_SHORT).show();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		 
		 Log.e("dasfs", "dsfdsfsdf344444s");
		 params.add(new BasicNameValuePair("latlng","17.376"+","+"78.560"));
		 params.add(new BasicNameValuePair("sensor","false"));
		 json = jParser.makeHttpRequest(get_latlng, "GET", params);
		 

			if(json!=null){
				//Toast.makeText(getApplicationContext(), jsondir.toString(), Toast.LENGTH_LONG).show();
				try {
					JSONObject j1=json.getJSONArray("results").getJSONObject(0);
					String add=j1.getString("formatted_address");
					Log.e("Inside intent  ", add);
					
					IncommingCallReceiver inc=new IncommingCallReceiver();
					inc.displayIncomming(add);
					
					Toast.makeText(getApplicationContext(), add, Toast.LENGTH_SHORT).show();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				
				
				
			}
			else{
				Log.e("Inside Service", "error error error");
				//Toast.makeText(this, "Error Error", Toast.LENGTH_SHORT).show();
			}
			
		 
		
	}
	

}
