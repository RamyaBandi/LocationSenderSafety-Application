package com.example.displayloaction;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.StringTokenizer;



import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.content.ContentResolver;
import android.content.DialogInterface;

import android.database.Cursor;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Contacts_activity extends Activity implements OnItemClickListener, android.view.View.OnClickListener {

	

		ListView l;
		ArrayAdapter<String> adapter;
		ArrayList<String> contact_list, select_list;
		Button save;
		String text;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.list_contacts);
			l  = (ListView) findViewById(R.id.contacts_list);
			l.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			contact_list = new ArrayList<String>();
			select_list = new ArrayList<String>();
			fetchContacts();
			adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,contact_list);
			
			l.setAdapter(adapter);
			l.setOnItemClickListener(this);
			//save = (Button) findViewById(R.id.save);
			//save.setOnClickListener(this);

		}

		public void fetchContacts() {



			String phoneNumber = null;

			//String email = null;



			Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

			String _ID = ContactsContract.Contacts._ID;

			String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;

			String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;



			Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

			String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

			String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;



			//Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;

			//String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;

			//String DATA = ContactsContract.CommonDataKinds.Email.DATA;



			StringBuffer output = new StringBuffer();



			ContentResolver contentResolver = getContentResolver();



			Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null); 



			// Loop for every contact in the phone

			if (cursor.getCount() > 0) {



				while (cursor.moveToNext()) {



					String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));

					String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));



					int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));



					if (hasPhoneNumber > 0) {



						output.append(""+name+"\n");



						// Query and loop for every phone number of the contact

						Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);



						while (phoneCursor.moveToNext()) {

							phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));

							output.append(""+ phoneNumber);



						}



						phoneCursor.close();



						/*	// Query and loop for every email of the contact

						Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);



						while (emailCursor.moveToNext()) {



							email = emailCursor.getString(emailCursor.getColumnIndex(DATA));



							output.append("\nEmail:" + email);



						}



						emailCursor.close();

					}
						 */


						output.append("\n");

					}



					contact_list.add(output.toString());
					output = new StringBuffer();

				}

			}


		}

		/*@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(),"item selected", Toast.LENGTH_SHORT).show();
			TextView t = (TextView) arg1;
			String text = (String) t.getText();
			select_list.add(text);
		
			
			Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
		}*/
		
		
		

		

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
			int k = 0;
			String number[] = new String[20],name[] = new String[20];
			for(int i = 0; i < 20; i++){
				name[i] = null;
				number[i] = null;
			}
			
			for(int j = 0; j < select_list.size(); j++){
				String text = select_list.get(j);
				StringTokenizer st = new StringTokenizer(text,"\n");
				name[k] = st.nextToken();
				number[k] = st.nextToken();
				k++;
				
			
			}
			for(int i=0;i<select_list.size();i++)
			{
				Toast.makeText(getApplicationContext(), number[i], Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getApplicationContext(),"item selected", Toast.LENGTH_SHORT).show();
			TextView t = (TextView) arg1;
			 text = (String) t.getText();
			//select_list.add(text);
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			 
            // Setting Dialog Title
            alertDialog.setTitle("Save File...");

            // Setting Dialog Message
            alertDialog.setMessage("Do you want to save this file?");

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.save);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on YES",
                                    Toast.LENGTH_SHORT).show();
               
                Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
                
                TestAdapter mDbHelper=new TestAdapter(getApplicationContext());
                mDbHelper.createDatabase();
          	    mDbHelper.open();
          	    Cursor testdata = mDbHelper.getTestData(); 
          	    String n=testdata.getString(testdata.getColumnIndex("phone_num"));
          	  Toast.makeText(getApplicationContext(),n,
                      Toast.LENGTH_SHORT).show();
                
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // User pressed No button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                }
            });

            // Setting Netural "Cancel" Button
            alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // User pressed Cancel button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                                    Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
		
			
			
		}
	}

	

