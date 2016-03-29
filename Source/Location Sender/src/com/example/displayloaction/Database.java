package com.example.displayloaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database {

	 public static final String KEY_ROWID = "_id";
	 public static final String PHONE_NUM="phno";
	 public static final String DNAME="dname";
	 
	 public static final String STATE="state";

	    private static final String DATABASE_TABLE2 = "sttab";
	 
	 private static final String TAG = "Database";
	 
	 
	 	private static final String DATABASE_NAME = "Mylt";
	    private static final String DATABASE_TABLE = "adcon";
	    private static final int DATABASE_VERSION = 1;
	    
	    private static final String DATABASE_CREATE =
	            "create table adcon (_id integer primary key autoincrement,phno text not null,dname text not null);";

	    private static final String DATABASE_CREATE2 =
	            "create table sttab (state integer);";
	            
	        private final Context context;    

	        private DatabaseHelper DBHelper;
	        private SQLiteDatabase db;
	        
	        public Database(Context ctx) 
	        {
	            this.context = ctx;
	            DBHelper=new DatabaseHelper(context);
	        }
	            
	        private static class DatabaseHelper extends SQLiteOpenHelper {

	        	DatabaseHelper(Context context) 
	            {
	                super(context, DATABASE_NAME, null, DATABASE_VERSION);
	            }

				@Override
				public void onCreate(SQLiteDatabase db) {
					// TODO Auto-generated method stub

		        	try {
		        		db.execSQL(DATABASE_CREATE);
		        		Log.e("dtata table created","Created 444");
		        	} catch (SQLException e) {
		        		e.printStackTrace();
		        	}
		        	
		        	try {
		        		db.execSQL(DATABASE_CREATE2);
		        		Log.e("dtata tabl222e created","Created 222");
		        		ContentValues initialValues = new ContentValues();
		        		initialValues.put(STATE,1);
		        		db.insert(DATABASE_TABLE2,null,initialValues);
		        		
		        		
		        	} catch (SQLException e) {
		        		e.printStackTrace();
		        	}
				}

				@Override
				public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					// TODO Auto-generated method stub

		            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
		                    + newVersion + ", which will destroy all old data");
		            db.execSQL("DROP TABLE IF EXISTS contacts");
		            onCreate(db);
					
				}
	        	
	        }
	        
	      //---opens the database---
	        public Database open() throws SQLException 
	        {
	            db = DBHelper.getWritableDatabase();
	            return this;
	        }

	        //---closes the database---    
	        public void close() 
	        {
	            DBHelper.close();
	        }   
	        
	        public void insertContact(String num, String name) 
	        {
	            ContentValues initialValues = new ContentValues();
	            
	            initialValues.put(PHONE_NUM, num);
	            initialValues.put(DNAME,name);
	             db.insert(DATABASE_TABLE, null, initialValues);
	        }
	      

			public Cursor getSavedContacts() {
				// TODO Auto-generated method stub
				 Cursor sor =
			                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,PHONE_NUM,DNAME}, null , null,
			                null, null, null, null);
				return sor;
			}

			public Cursor getContactDetails(String num) {
				// TODO Auto-generated method stub
				Log.e("dsdnumber",num);
				String[] arg = {num};
				Cursor mor=db.rawQuery("SELECT * from adcon where phno=?", arg);
				//Cursor mor=db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,PHONE_NUM,DNAME,GENDER}, PHONE_NUM +"="+"'"+num+"'", null,null,null,null,null);
				 if (mor != null) {
					 Log.e("dsds",mor.getCount()+"");
			            mor.moveToFirst();
			            Log.e("firsttstss",mor.getCount()+""); 
			            return mor;
			        }
				return null;
			}

			public void deleteSavedContact(String pp) {
				// TODO Auto-generated method stub
				String[] args={pp};
				db.delete(DATABASE_TABLE, "phno=?", args);
				Log.e("deleted", "sxscs");
			}
	        
			
			public void changeState(int x) {
				// TODO Auto-generated method stub
				
				ContentValues inValues=new ContentValues();
				inValues.put(STATE,x);
				db.update(DATABASE_TABLE2, inValues,null, null);
				
			}

			public Cursor getState() {
				// TODO Auto-generated method stub
				Cursor c=db.rawQuery("Select * from sttab",null);
				
				return c;
			}
	        
	        
	        
	    
}
