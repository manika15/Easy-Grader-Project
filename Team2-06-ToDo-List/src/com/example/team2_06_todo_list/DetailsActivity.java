package com.example.team2_06_todo_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DetailsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

//		Button btn_signup = (Button)findViewById(R.id.btn_main_signup);
//		btn_signup.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
//	            startActivity(intent);
//	            finish();
//			}
//		});
//		
//		Button btn_cancel = (Button)findViewById(R.id.btn_signup_cancel);
//		btn_cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//	            finish();
//			}
//		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		//menu.findItem(R.id.menu_item_new).setIntent(new Intent(MainActivity.this, LoginActivity.class));
		return true;
	}

	
}
