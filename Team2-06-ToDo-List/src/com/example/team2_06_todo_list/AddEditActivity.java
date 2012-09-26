package com.example.team2_06_todo_list;

import com.example.database.DataSource;
import com.example.database.ToDoItems;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditActivity extends Activity {

	private DataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        datasource = new DataSource(this);
        datasource.open();
        
        Button btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        EditText txt_task = (EditText)findViewById(R.id.EditText_task);
		        String str_task =  txt_task.getText().toString();
				datasource.createItem(str_task);
				finish();
			}
		});
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.findItem(R.id.menu_item_new).setIntent(new Intent(AddEditActivity.this, AddEditActivity.class));
		menu.findItem(R.id.menu_item_Hidden).setIntent(new Intent(AddEditActivity.this, HiddenItemsActivity.class));
        return true;
    }
}
