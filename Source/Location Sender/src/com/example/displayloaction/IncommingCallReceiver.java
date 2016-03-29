package com.example.displayloaction;





import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class IncommingCallReceiver extends BroadcastReceiver {

	 static boolean ring=false;
     static boolean callReceived=false;
	Context context;
	String callernumber;
	String setaddr;
	int s;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		 Bundle bundle = intent.getExtras();
         callernumber= bundle.getString("incoming_number");
		Toast.makeText(context, "it is a call from "+callernumber, Toast.LENGTH_LONG).show();
		

        // Get the current Phone State
       String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
       
       if(state==null)
           return;

       // If phone state "Rininging"
       if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
       {           
                 ring =true;
                // Get the Caller's Phone Number
              //  Bundle bundle = intent.getExtras();
              //  callernumber= bundle.getString("incoming_number");
         }



        // If incoming call is received
       if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
        {
               callReceived=true;
         }


        // If phone is Idle
       if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
       {
                 // If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
                  if(ring==true&&callReceived==false)
                  {
                           Toast.makeText(context, "It was A MISSED CALL from : "+callernumber, Toast.LENGTH_LONG).show();
                          
                           
                           Database data=new Database(context);
                   		data.open(); 
                   		 Cursor c2 = data.getState();
                   	     if (c2.moveToFirst()) 
                   	     {
                   	        s=c2.getInt(0);
                   	         c2.close();
                   	     }
                   	  else
             	         Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
             	     data.close();
                           
                     if(s==1){      
                           
                   		Database db=new Database(context);
                   		db.open();
                           Cursor c = db.getContactDetails(callernumber);
                           Log.e("hgfhfghfhgfsdds",c.getCount()+"");
                           if(c.getCount()>0){
                           	if(c.moveToFirst())
                           	{
                           	String g = c.getString(c.getColumnIndex("dname")); 
                           	Log.e("dbhagdjhagd",g);
                           	Toast.makeText(context, g, Toast.LENGTH_LONG).show();
                           
                            Intent i=new Intent(context,SendActivity.class);
                            i.putExtra("flag1", 1);
                            i.putExtra("phonenum", callernumber);
                    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    		context.startActivity(i);
                           	
                           	}
                           }
                           c.close();
                           db.close();
                     }  
                     else{
                    	 Toast.makeText(context, "Your location tracker is off", Toast.LENGTH_SHORT).show();
     		    	    
                     }
                           
                  }
     }
		
		
				
      // Intent in=new Intent(context, MyIntentService.class);
     //  context.startService(in);
       
        
        
		//Toast.makeText(context, setaddr+"hellow", Toast.LENGTH_LONG).show();
	}

	void displayIncomming(String add){

		Log.e("Inside sms", add);
		
		//Toast.makeText(context, add+"hellow", Toast.LENGTH_LONG).show();
	}
	
	
}
