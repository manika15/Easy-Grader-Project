package com.example.team2_06_todo_list;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HiddenItemsActivity extends Activity {

	private String[] items = { "Meet the professor", "Submit the assignment", "Class at 11", "Deposit cash" };
	private String[] Date = { "Sep 15", "Sep 30", "Oct 1", "Dec 25" };

	static final String KEY_ToDoId = "id"; // parent node
	static final String KEY_ToDo = "todo"; 
	static final String KEY_PREFERENCE = "id";
	static final String KEY_DATE = "title";
	static final String KEY_STATUS = "artist";

	private static ArrayList<HashMap<String, String>> ToDoArrayList = new ArrayList<HashMap<String,String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_items);

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
		
		ListView lst_todo = (ListView)findViewById(R.id.hidden_listView);
		EfficientAdapter adapter = new EfficientAdapter(this);
		lst_todo.setAdapter(adapter);
	
//		Button btn_cancel = (Button)findViewById(R.id.btn_unh);
//		btn_cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.findItem(R.id.menu_item_new).setIntent(new Intent(HiddenItemsActivity.this, AddEditActivity.class));
		menu.findItem(R.id.menu_item_Hidden).setIntent(new Intent(HiddenItemsActivity.this, HiddenItemsActivity.class));
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
				holder.txtDuePriority = (TextView) convertView.findViewById(R.id.txt_list_due_prioroty);
				
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
			TextView txtDuePriority;
		}
	}
}
