package com.example.displayloaction;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Backgroundtracker{
String addressbt;
Context context;
public MyInterface mine=null;
public void btstart(){
	new LoadAllp().execute();
	
	
}
  public class LoadAllp extends AsyncTask<String, String, String> {

	JSONParser  jParser =  new JSONParser();
	
	 JSONObject json;
	 String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
	 String add;
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
		 
		 Log.e("dasfs", "dsfdsfsdf344444s");
		 params.add(new BasicNameValuePair("latlng","17.376"+","+"78.560"));
		 params.add(new BasicNameValuePair("sensor","false"));
		 json = jParser.makeHttpRequest(get_latlng, "GET", params);
	return null;	
	}		
	
	
	protected void onPostExecute(String file_url) {
		// dismiss the dialog after getting all products
	
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
				Log.e("Inside bt", add);
				addressbt=add;
				
				//IncommingCallReceiver m=new IncommingCallReceiver();
				//m.setaddr=add;
				mine.processFinish(add);
				//Toast.makeText(context, add, Toast.LENGTH_SHORT).show();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			
			
			
		}
		else{
			Log.e("Inside bt", "error");
			//Toast.makeText(this, "Error Error", Toast.LENGTH_SHORT).show();
		}
		
		
		
		
	}
	

}

}
