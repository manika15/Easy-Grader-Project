package com.example.team2_06_todo_list;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String[] items = { "Meet the professor", "Submit the assignment", "Class at 11", "Deposit cash" };
	private String[] Date = { "Sep 15", "Sep 30", "Oct 1", "Dec 25" };

	static final String KEY_ToDoId = "id"; // parent node
	static final String KEY_ToDo = "todo"; 
	static final String KEY_PREFERENCE = "id";
	static final String KEY_DATE = "title";
	static final String KEY_STATUS = "artist";
	
	public static final int SetSortDate = 0;
    public static final int SetPrioritySort = 1;
    
	private static ArrayList<HashMap<String, String>> ToDoArrayList = new ArrayList<HashMap<String,String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		HashMap<String, String> ToDo_hash = new HashMap<String, String>();
		ToDo_hash.put(KEY_ToDo, items[0]);
		ToDo_hash.put(KEY_DATE, Date[0]);
		ToDoArrayList.add(ToDo_hash);
		ToDo_hash.put(KEY_ToDo, items[1]);
		ToDo_hash.put(KEY_DATE, Date[1]);
		
		ToDoArrayList.add(ToDo_hash);
		ToDo_hash.put(KEY_ToDo, items[2]);
		ToDo_hash.put(KEY_DATE, Date[2]);
		ToDoArrayList.add(ToDo_hash);
		ToDo_hash.put(KEY_ToDo, items[3]);
		ToDo_hash.put(KEY_DATE, Date[3]);

		ToDoArrayList.add(ToDo_hash);
		
		ListView lst_todo = (ListView)findViewById(R.id.main_listView);
			
		EfficientAdapter adapter = new EfficientAdapter(this);
		lst_todo.setAdapter(adapter);
		registerForContextMenu(lst_todo);
		lst_todo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
	            startActivity(intent);
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SetSortDate:
			break;
		case SetPrioritySort:
			break;

		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Menu");
		menu.add(0, SetSortDate, 0, "Sort by date");
		menu.add(0, SetPrioritySort, 1, "Sort by priority");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.findItem(R.id.menu_item_new).setIntent(new Intent(MainActivity.this, AddEditActivity.class));
		menu.findItem(R.id.menu_item_Hidden).setIntent(new Intent(MainActivity.this, HiddenItemsActivity.class));
		return true;
	}

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
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_listview, null);
				holder = new ViewHolder();
				
				holder.txtTodoItem = (TextView) convertView.findViewById(R.id.txt_list_todo);
				holder.txtDueDate = (TextView) convertView.findViewById(R.id.txt_list_due_date);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			String str = ToDoArrayList.get(position).get(KEY_ToDo);
			holder.txtTodoItem.setText(str);
			String ster_dae = ToDoArrayList.get(position).get(KEY_DATE);
			holder.txtDueDate.setText(ster_dae);
			//i++;
			return convertView; 

		}

		static class ViewHolder {
			TextView txtTodoItem;
			TextView txtDueDate;
		}
	}

}
