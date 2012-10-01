package com.example.team2_06_todo_list;

import java.util.Calendar;

import com.example.database.DataSource;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditActivity extends Activity {

	private DataSource datasource;

	// int fields to open coresponding dialog boxes for the dat and time fields
	static final int STARTDATE_DAILOG_ID = 0;
	Time startDateValue = new Time();
	long startDateFinalValue=0;
	String user_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit);

		datasource = new DataSource(this);
		datasource.open();

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// Get data via the key
		user_id = extras.getString("user_id");

		ImageButton pickStartDate = (ImageButton) findViewById(R.id.ImageButton_DueDate);
		pickStartDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(STARTDATE_DAILOG_ID);
			}
		});



		//set the start date to the current date
		final TextView startDateLabel = (TextView) findViewById(R.id.TextView_StartDate);
		startDateValue.setToNow();
		startDateFinalValue = startDateValue.toMillis(true);
		startDateLabel.setText(DateFormat.format("MMMM dd, yyyy", startDateFinalValue));

		//set the start time to the current date
		//        final TextView startTimeLabel = (TextView) findViewById(R.id.TextView_CreateInvite_StartTime_Label);
		//        startTimeLabel.setText("(pick start time)");

		Button btn_save = (Button)findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
					if(!validate_Fields()){
						return;
					}

					add_item();
					Intent intent = new Intent(AddEditActivity.this, MainActivity.class);
					intent.putExtra("user_id", user_id);
					startActivity(intent);


				}
				catch(Exception e)
				{

				}


			}
		});

		Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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

	private void add_item()
	{
		EditText txt_item = (EditText)findViewById(R.id.EditText_task);
		EditText txt_desc = (EditText)findViewById(R.id.editNote);
		Spinner spinner_priority = (Spinner)findViewById(R.id.spinnerPriority);

		String str_Item = txt_item.getText().toString();
		String str_Desc = txt_desc.getText().toString();
		String DueDate = DateFormat.format("MM dd yyyy", startDateFinalValue).toString();
		String Priority = spinner_priority.getSelectedItem().toString();
		datasource.createItem(str_Item, DueDate, str_Desc, Priority, user_id, "1");

	}

	private DatePickerDialog.OnDateSetListener mDateSetListenerForStartDate = new DatePickerDialog.OnDateSetListener()
	{
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			try
			{
				final TextView startDateLabel = (TextView) findViewById(R.id.TextView_StartDate);

				startDateValue.set(dayOfMonth, monthOfYear, year);
				startDateFinalValue = startDateValue.toMillis(true);
				startDateLabel.setText(DateFormat.format("MMMM dd, yyyy", startDateFinalValue));

			}
			catch(Exception ex)
			{
				Log.v("Logged error : ", "TimePickerDialog.OnTimeSetListener in CreateNewInviteActivity, userid");
			}
		}
	};

	@Override
	protected Dialog onCreateDialog(int id)
	{
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);

		switch (id)
		{
		case STARTDATE_DAILOG_ID:  // for start date picker
			return new DatePickerDialog(this,  mDateSetListenerForStartDate,  cyear, cmonth, cday);

		}
		return null;
	}

	private Boolean validate_Fields ()
	{
		EditText txt_userName = (EditText)findViewById(R.id.EditText_task);
		EditText txt_pass = (EditText)findViewById(R.id.EditText_Password);

		String str_user = txt_userName.getText().toString();
		String str_pass = txt_pass.getText().toString();

		if(str_user.equals(""))
		{
			showMessageToast(AddEditActivity.this, "Please enter task.!");
			return false;
		}


		return true;
	}


	public void showMessageToast(Activity activityId,String message) {
		try
		{
			Toast tst = Toast.makeText(activityId, message, Toast.LENGTH_LONG);
			tst.setGravity(Gravity.CENTER, tst.getXOffset() / 2, tst.getYOffset() / 2);
			tst.show();
		}
		catch(Exception ex)
		{
			Log.v("Logged error : ", "showMessageToast() in MeetingWaveMainClass, userid");

		}
	}

	//	@Override
	//	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	//
	//		if (keyCode == KeyEvent.KEYCODE_BACK)  //Override Keyback to do nothing in this case.
	//		{
	//			//add_item();
	//			//return true;
	//		}
	//		return super.onKeyDown(keyCode, event);  //-->All others key will work as usual
	//	}
}
