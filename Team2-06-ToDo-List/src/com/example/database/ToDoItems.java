package com.example.database;

public class ToDoItems {

	  private long id;
	  private String ToDoItem;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getToDoItem() {
	    return ToDoItem;
	  }

	  public void setToDoItem(String comment) {
	    this.ToDoItem = comment;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return ToDoItem;
	  }
}
