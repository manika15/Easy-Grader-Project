package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_ITEM };

	  public DataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  public ToDoItems createItem(String item) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_ITEM, item);
	    long insertId = database.insert(MySQLiteHelper.TABLE_ToDoList, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_ToDoList,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    ToDoItems newItem = cursorToItems(cursor);
	    cursor.close();
	    return newItem;
	  }

	  public void deleteComment(ToDoItems item) {
	    long id = item.getId();
	    System.out.println("Item deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_ToDoList, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<ToDoItems> getAllComments() {
	    List<ToDoItems> items = new ArrayList<ToDoItems>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_ToDoList,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ToDoItems item = cursorToItems(cursor);
	      items.add(item);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return items;
	  }

	  private ToDoItems cursorToItems(Cursor cursor) {
	    ToDoItems item = new ToDoItems();
	    item.setId(cursor.getLong(0));
	    item.setToDoItem(cursor.getString(1));
	    return item;
	  }
}
