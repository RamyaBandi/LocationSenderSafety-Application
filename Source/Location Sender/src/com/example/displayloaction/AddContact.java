package com.example.displayloaction;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddContact extends ListActivity {

	final Context context1 = this;
	Button inbt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		inbt=(Button) findViewById(R.id.inbt);
		inbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 AlertDialog.Builder alert = new AlertDialog.Builder(AddContact.this);
				 alert.setTitle("Add Number");
				 LinearLayout layout = new LinearLayout(context1);
				 layout.setOrientation(LinearLayout.VERTICAL);

				 final EditText titleBox = new EditText(context1);
				 titleBox.setHint("Phone Number");
				 layout.addView(titleBox);

				 final EditText descriptionBox = new EditText(context1);
				 descriptionBox.setHint("Name");
				 layout.addView(descriptionBox);

				 alert.setView(layout);
		            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                 //You will get as string input data in this variable.
		                 // here we convert the input to a string and show in a toast.
		                 String srt = titleBox.getEditableText().toString();
		                 String dname = descriptionBox.getEditableText().toString();
		                 

		              Database data=new Database(getApplicationContext());
		     			data.open(); 
		     			  data.insertContact(srt, dname);
		                data.close(); 
		                 
		                Intent i=getIntent();
		        		finish();
		        		startActivity(i);
		                 Toast.makeText(context1,srt+dname,Toast.LENGTH_LONG).show();                
		                } // End of onClick(DialogInterface dialog, int whichButton)
		            });
		            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                  // Canceled.
		                    dialog.cancel();
		                }
		          }); //End of alert.setNegativeButton
		              AlertDialog alertDialog = alert.create();
		              alertDialog.show();
		              
			}
		});
		
		
		
		//Start of Displaying exsiting contacts
		Database db=new Database(getApplicationContext());
		db.open();
        Cursor c = db.getSavedContacts();
        startManagingCursor(c);
        String[] from = {"_id","dname","phno" };
        int[] to = {R.id.rowid,R.id.phnoid,R.id.dnameid };
        
        
		SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.listentryview,c,from,to);
        setListAdapter(adapter);
        
        
        db.close();
        
		
		
		
		
}
	
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	    super.onListItemClick(l, v, position,  id);
	    
	    
	    Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
	    c.moveToPosition(position);

	    final String pp = c.getString(1);
	  
	    
	    
	    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
	    
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");
 
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");
 
        // Setting Icon to Dialog
       // alertDialog.setIcon(R.drawable.delete);
 
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
 

        		Database db=new Database(getApplicationContext());
        		db.open();
        		db.deleteSavedContact(pp);
            	
        		db.close();
        		dialog.cancel();
        		
        		Intent i=getIntent();
        		finish();
        		startActivity(i);
        		
            // Write your code here to invoke YES event
            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });
 
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // Write your code here to invoke NO event
            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	    
	    
	    
	   
	   
	    //imgv.setImageBitmap(BitmapFactory.decodeFile(pp));
	   Toast.makeText(getApplicationContext(), pp, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

}
