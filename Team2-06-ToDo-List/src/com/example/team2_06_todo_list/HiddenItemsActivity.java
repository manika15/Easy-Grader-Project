package com.example.team2_06_todo_list;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DataSource;

public class HiddenItemsActivity extends Activity {
	private DataSource datasource;


	String user_id;
	String item_id;

	
	static final String ITEM_id = "item_id";
	static final String ITEM = "item";
	static final String DUE_DATE = "due_date";     
	static final String DESCRIPTION = "description";
	static final	String PRIOITY = "priority";
	static final String STATUS = "status";
	static final String REFRENCE_ID = "reference_id";

	private static ArrayList<HashMap<String, String>> ToDoArrayList = new ArrayList<HashMap<String,String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_items);
		
		datasource = new DataSource(this);
		datasource.open();

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// Get data via the key
		user_id = extras.getString("user_id");
		item_id = extras.getString("item_id");

		fillData();

		ListView lst_todo = (ListView)findViewById(R.id.hidden_listView);
		EfficientAdapter adapter = new EfficientAdapter(this);
		lst_todo.setAdapter(adapter);

				Button btn_cancel = (Button)findViewById(R.id.btn_unhide_delete);
				btn_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}

	//    @Override
	//    public boolean onCreateOptionsMenu(Menu menu) {
	//		getMenuInflater().inflate(R.menu.activity_main, menu);
	//		menu.findItem(R.id.menu_item_new).setIntent(new Intent(HiddenItemsActivity.this, AddActivity.class));
	//		menu.findItem(R.id.menu_item_Hidden).setIntent(new Intent(HiddenItemsActivity.this, HiddenItemsActivity.class));
	//        return true;
	//    }

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

		}

		public int getCount() {
			return ToDoArrayList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_listview, null);
				holder = new ViewHolder();

				holder.txtTodoItem = (TextView) convertView.findViewById(R.id.txt_list_todo);
				holder.txtDueDate = (TextView) convertView.findViewById(R.id.txt_list_due_date);
				//holder.txtPriority = (TextView) convertView.findViewById(R.id.txt_list_priority);
				holder.imgPriority = (ImageView) convertView.findViewById(R.id.txt_list_priority);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}



			if(ToDoArrayList.get(position).get(STATUS).equals("1"))
			{
				String str_Item = ToDoArrayList.get(position).get(ITEM);
				if(str_Item.length() > 15)
				{
					str_Item = str_Item.substring(0,15) + "...";
				}
				holder.txtTodoItem.setText(str_Item);
				holder.txtDueDate.setText(ToDoArrayList.get(position).get(DUE_DATE));
				//holder.txtPriority.setText(ToDoArrayList.get(position).get(PRIOITY));
				String str_priority = ToDoArrayList.get(position).get(PRIOITY);

				if(str_priority.equals("1"))
				{
					holder.imgPriority.setImageResource(R.drawable.red);
				}
				else if(str_priority.equals("2"))
				{
					holder.imgPriority.setImageResource(R.drawable.blue);
				}
				else if(str_priority.equals("3"))
				{
					holder.imgPriority.setImageResource(R.drawable.yellow);
				}
			}



			//i++;
			return convertView; 


		}

		static class ViewHolder {
			TextView txtTodoItem;
			TextView txtDueDate;
			ImageView imgPriority;
		}
	}

	private void fillData() {

		try
		{
			ToDoArrayList = new ArrayList<HashMap<String,String>>();
			DataSource mDbadapter = new DataSource(this);
			mDbadapter.open(); 
			Cursor data_cur = mDbadapter.fetchAllItems(user_id);
			if(data_cur.getCount() >0)
			{ 
				if(data_cur.moveToFirst()) 
				{ 
					do   
					{
						HashMap<String,String> map = new HashMap<String, String>(); 

						String item_id = data_cur.getString(0);
						String item = data_cur.getString(1);
						String due_date = data_cur.getString(2);     
						String description = data_cur.getString(3);
						String priority = data_cur.getString(4);
						String status = data_cur.getString(5);
						//							int refrence_id = data_cur.getInt(6);
						//							int sa = refrence_id;

						map.put(ITEM_id, item_id);
						map.put(ITEM, item);
						map.put(DUE_DATE, due_date);
						map.put(DESCRIPTION, description);
						map.put(PRIOITY, priority);
						map.put(STATUS, status);
						//map.put(REFRENCE_ID, refrence_id);
						ToDoArrayList.add(map);

					}while (data_cur.moveToNext());

				}
			}

			else Toast.makeText(this, 
					"No Records", 
					Toast.LENGTH_SHORT).show();       


			data_cur.close();   
		}
		catch (Exception e) {
			// TODO: handle exception\
		}

	}

}
