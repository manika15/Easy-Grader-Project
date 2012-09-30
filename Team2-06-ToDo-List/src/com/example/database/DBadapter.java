package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBadapter {

	public static final String TABLE_ToDoList = "todo";
	public static final String COLUMN_ID = "item_id";
	public static final String COLUMN_ITEM = "item";
	public static final String COLUMN_DUE_DATE = "dudedate";
	public static final String COLUMN_DESC = "description";

	public static final String TABLE_USER = "user";
	public static final String USER_ID = "user_id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_EMAIL = "email";

	private static final String DATABASE_NAME = "tasks.db";
	private static final int DATABASE_VERSION = 1;

	private Context ctx;
	private DatabaseHelper dbhelper;
	private static SQLiteDatabase db;

	// Database creation sql statement
	private static final String DATABASE_USER = "create table "
			+ TABLE_USER + "(" + USER_ID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME
			+ " text not null, " + COLUMN_PASSWORD
			+ " text not null, " + COLUMN_EMAIL
			+ " null);";

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_ToDoList + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_ITEM
			+ " text, " + COLUMN_DUE_DATE
			+ " text, " + COLUMN_DESC
			+ " text, " + 
			" reference_id INTEGER NOT NULL," +
			" FOREIGN KEY(reference_id) REFERENCES user(user_id) ON DELETE CASCADE" +
			");";

	public DBadapter(Context ctx) {
		this.ctx = ctx;
	}

//	public DBadapter open() throws SQLException {
//		dbhelper = new DatabaseHelper(ctx);
//
//		try {
//
//			dbhelper.createDataBase();
//		} catch (IOException ioe) {
//			throw new Error("Unable to create database");
//		}
//
//		try {
//			dbhelper.openDataBase();
//		} catch (SQLException sqle) {
//			throw sqle;
//		}
//		return this;
//	}
	
		public DBadapter open() throws SQLException {
			dbhelper = new DatabaseHelper(ctx);
			db = dbhelper.getWritableDatabase();
	
			return this;
		}

	public void close() {
		dbhelper.close();
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		Context helperContext;

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			helperContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_USER);
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(MySQLiteHelper.class.getName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ToDoList);
			onCreate(db);
		}

//		public void createDataBase() throws IOException {
//			boolean dbExist = checkDataBase();
//			if (dbExist) {
//			} else {
//
//				//make sure your database has this table already created in it
//				//this does not actually work here
//				/*
//				 * db.execSQL("CREATE TABLE IF NOT EXISTS \"android_metadata\" (\"locale\" TEXT DEFAULT 'en_US')"
//				 * );
//				 * db.execSQL("INSERT INTO \"android_metadata\" VALUES ('en_US')"
//				 * );
//				 */
//				this.getReadableDatabase();
//				try {
//					copyDataBase();
//				} catch (IOException e) {
//					throw new Error("Error copying database");
//				}
//			}
//		}
//
//		private boolean checkDataBase() {
//			SQLiteDatabase checkDB = null;
//			try {
//				String myPath = DATABASE_NAME;
//				checkDB = SQLiteDatabase.openDatabase(myPath, null,
//						SQLiteDatabase.OPEN_READONLY);
//			} catch (SQLiteException e) {
//			}
//			if (checkDB != null) {
//				checkDB.close();
//			}
//			return checkDB != null ? true : false;
//		}
//
//		private void copyDataBase() throws IOException {
//
//			// Open your local db as the input stream
//			InputStream myInput = helperContext.getAssets().open(DATABASE_NAME);
//
//			// Path to the just created empty db
//			String outFileName = DATABASE_NAME;
//
//			// Open the empty db as the output stream
//			OutputStream myOutput = new FileOutputStream(outFileName);
//
//			// transfer bytes from the inputfile to the outputfile
//			byte[] buffer = new byte[1024];
//			int length;
//			while ((length = myInput.read(buffer)) > 0) {
//				myOutput.write(buffer, 0, length);
//			}
//
//			// Close the streams
//			myOutput.flush();
//			myOutput.close();
//			myInput.close();
//		}
//		
//		public void openDataBase() throws SQLException {
//			// Open the database
//			String myPath = DATABASE_NAME;
//			db = SQLiteDatabase.openDatabase(myPath, null,
//					SQLiteDatabase.OPEN_READWRITE);
//		}

	}

	public Cursor createUser(String username, String pass, String email)
	{
		String query = "INSERT into user values ("+null +",'"+ username +"','"+ pass +"','"+ email + "')";
		return db.rawQuery(query,null);

		//		  try{
		//			  return database.rawQuery(query,null);
		//		  }
		//		  catch(Exception e)
		//		  {
		//			  String str = e.getMessage();
		//		  }
		//		return null;
	}
}
