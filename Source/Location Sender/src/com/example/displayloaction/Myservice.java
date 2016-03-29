package com.example.displayloaction;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class Myservice extends Service{
	  BroadcastReceiver mReceiver;
	  public class MyReceiver extends BroadcastReceiver {

		  @Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
			  Toast.makeText(arg0,"phonecall",Toast.LENGTH_SHORT).show();
			  
			}
	    }

	    @Override
	    public void onCreate() {
	         // get an instance of the receiver in your service
	    	Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show();
	    	 IntentFilter filter = new IntentFilter();
	         filter.addAction("action");
	         filter.addAction("anotherAction");
	         mReceiver = new MyReceiver();
	         //registerReceiver(mReceiver, filter);
	         this.registerReceiver(mReceiver, filter);
	    }
	/*@Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		 Toast.makeText(this,"started",Toast.LENGTH_SHORT).show();
	    return Service.START_NOT_STICKY;
	   
	  }*/
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
