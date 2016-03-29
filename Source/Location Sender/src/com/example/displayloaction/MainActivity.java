package com.example.displayloaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;



public class MainActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener{

   
    
    LocationClient x,y;
    BroadcastReceiver mreceiver;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    double lat;
    double lng;
    Button button1,btsend;
    ToggleButton tgb;
    int s;
    
    ProgressDialog pDialog;
    String AddressString;
   
	 List<NameValuePair> params = new ArrayList<NameValuePair>();
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        
        
        x= new LocationClient(this,this,this);
        
        x.connect();
       editText1=(EditText)findViewById(R.id.editText1);
       editText2=(EditText)findViewById(R.id.editText2);
       editText3=(EditText)findViewById(R.id.editText3);
       button1=(Button) findViewById(R.id.button1);
       btsend=(Button) findViewById(R.id.btsend);
       
       tgb=(ToggleButton) findViewById(R.id.toggleonoff);
       
       Database data=new Database(getApplicationContext());
		data.open(); 
		 Cursor c2 = data.getState();
	     if (c2.moveToFirst()) 
	     {
	        s=c2.getInt(0);
	         c2.close();
	     }
	     else
	         Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
	     data.close();
       
        if(s==1){
        	tgb.setChecked(true);
        }
        else{
        	tgb.setChecked(false);
        }
      
        tgb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(tgb.isChecked()){
		        	Toast.makeText(getApplicationContext(), "Your location tracker is on", Toast.LENGTH_SHORT).show();
		        	Database data=new Database(getApplicationContext());
					data.open(); 
					Log.e("value value", "dsf");
					data.changeState(1);
					data.close();
		    	    
		        }
		        else{
		        	
		        	Database data=new Database(getApplicationContext());
					data.open(); 
					Log.e("value value", "sds");
					data.changeState(0);
					data.close();
		        	
		        	Toast.makeText(getApplicationContext(), "Your location tracker is off", Toast.LENGTH_SHORT).show();
		    	    
		        }
			}
		});
        
        
       //Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();
	    //Location l1=new Location(x.getLastLocation());
	    		
	   
		//editText1.setText("l1.getLatitude()+");
	    //editText2.setText("l1.getLongitude()+");
	    
       //Intent s= new Intent(this, Myservice.class);
       //this.startService(s);
      // Intent clf= new Intent(this,ContactListFragment.class);
       //startActivity(clf); 
       button1.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Perform action on click
        	   
                //Toast.makeText(getBaseContext(),"clicked",Toast.LENGTH_SHORT).show();
        	     Intent con=new Intent(getBaseContext(),AddContact.class);
        	        
        	     startActivity(con);
           }
       });
       btsend.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Perform action on click
        	   int i=0;
                //Toast.makeText(getBaseContext(),"clicked",Toast.LENGTH_SHORT).show();
        	   Database db=new Database(getApplicationContext());
       		db.open();
               Cursor c = db.getSavedContacts();
               int count1=c.getCount();
               if (c != null) {
					 Log.e("dsds",c.getCount()+"");
			            c.moveToFirst();
			            Log.e("firsttstss",c.getCount()+""); 
			           
			        }
               if(count1<5){
            	   while (c.isAfterLast() == false) {
                   
            		String phno=c.getString(1);
             	   Log.e("fisasa",c.getCount()+""); 
             	  send5messages(phno);
                   	c.moveToNext();
                   
                   }
             
               }
               else{
            	   while (c.isAfterLast() == false&&i<5) {
                       
               		String phno=c.getString(1);
                	   Log.e("fisasa",c.getCount()+""); 
                	  send5messages(phno);
                      	c.moveToNext();
                      	i++;
                      
                      }
            	   
               }
           
               
               db.close();
           }
       });
    
    
    }
    
    	void send5messages(String phno){
    		
    		SmsManager smsManager = SmsManager.getDefault();
    	    smsManager.sendTextMessage(phno, null, "Location: "+AddressString, null, null);
    	    Toast.makeText(this,"SMS sent"+phno,Toast.LENGTH_SHORT).show();
    		
    	}
    	
    	
   
   
   //protected void onStop(){
	  // Toast.makeText(this,"stopped",Toast.LENGTH_SHORT).show();
	  // mreceiver = new MyReceiver();
   //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
   
	@Override
	public void onConnectionFailed(ConnectionResult result) {
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
		editText1.setText(lat+"");
	    editText2.setText(lng+"");
	  // getAddressString();
	   
	  new LoadAllProducts().execute();
	 // ConvertToAddress();
	   // String address=ConvertPointToLocation();
       // Toast.makeText(getBaseContext(), address, Toast.LENGTH_LONG).show();
        //mapView.invalidate();
	   // editText3.setText(address);
	   // mreceiver = new MyReceiver();
       
	}
	
	
	
	
	
	
	
	class LoadAllProducts extends AsyncTask<String, String, String> {

		
		 JSONParser  jParser =  new JSONParser();
			
		 JSONObject json;
		 String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
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
					AddressString=add;
					editText3.setText(add);
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
	
	
	
	/*
	 private void ConvertToAddress() {
		// TODO Auto-generated method stub
		 
		 String get_latlng = "http://maps.googleapis.com/maps/api/geocode/json";
		 
		 Log.e("dasfs", "dsfdsfsdf344444s");
		 params.add(new BasicNameValuePair("latlng",lat+","+lng));
		 params.add(new BasicNameValuePair("sensor","false"));
		 json = jParser.makeHttpRequest(get_latlng, "GET", params);
		 Log.e("dasfs", "dsfdsfsdfs");
		 if(json != null){
			 
			 Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
		 }
		 else{
				Toast.makeText(getApplicationContext(), "JSON NULL Returned", Toast.LENGTH_LONG).show();
		 }
		 
		 
		//return null;
	}*/

   public String getAddressString(){
    	Backgroundtracker bt=new Backgroundtracker();
    	
    	
    	bt.btstart();
    	Log.e("Inside main", AddressString+"hshhtte");
    	//editText3.setText(AddressString);
    return AddressString;	
    }


public void setAddfrombt(String addr) {
	AddressString=addr;
	Log.e("Inside main", AddressString+"hshhtte");
	displayAddress(AddressString);
}

	private String displayAddress(String addressString2) {
	// TODO Auto-generated method stub
		
		//editText3.setText(addressString2);
	return addressString2;
}






	public String ConvertPointToLocation() {   
		 
		 
		
		 
		 Log.v("I am called", "inside");
         String address = "";
         Geocoder geoCoder = new Geocoder(
                 getBaseContext(), Locale.getDefault());
         try {
             List<Address> addresses = geoCoder.getFromLocation( lat,lng, 1);

             if (addresses.size() > 0) {
                 for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
                     address += addresses.get(0).getAddressLine(index) + " ";
             }
         }
         catch (IOException e) {                
             e.printStackTrace();
         }     
         
         
		return address;
        
     } 

	
	

	
	
	
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
		
		Toast.makeText(this,"disconnected",Toast.LENGTH_SHORT).show();
		
	}






	
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		
		Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
		
	}
	
	 /* public class MyReceiver extends BroadcastReceiver {

	       

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
	            
	            Toast.makeText(context, 
	              "Outgoing: "+number,  
	              Toast.LENGTH_LONG).show();
			}

	 }     */
    
}
