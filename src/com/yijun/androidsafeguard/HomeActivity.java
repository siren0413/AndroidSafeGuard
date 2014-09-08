package com.yijun.androidsafeguard;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private GridView gv_home_body;
	private static final String[] names = {
		"Anti-Theft","Anti-Spam","App-Manager",
		"Task-Manager","Data-Usage","Anti-Virus",
		"Cleanup","Tools","Settings"
		};
	private static final int[] ids = {
		R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
		R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
		R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings
	};
	
	private static final int ANTI_THEFT = 0; 
	private static final int ANTI_SPAM = 1; 
	private static final int APP_MANAGER = 2; 
	private static final int TASK_MANAGER = 3; 
	private static final int DATA_USAGE = 4; 
	private static final int ANTI_VIRUS = 5; 
	private static final int CLEAN_UP = 6; 
	private static final int TOOLS = 7; 
	private static final int SETTINGS = 8; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//initial widgets
		gv_home_body = (GridView) findViewById(R.id.gv_home_body);
		
		gv_home_body.setAdapter(new BodyAdapter());
		gv_home_body.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case SETTINGS:
					Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}
	
	
	private class BodyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.grid_item_home, null);
			TextView tv_home_grid_item = (TextView)view.findViewById(R.id.tv_home_grid_item);
			ImageView iv_home_grid_item = (ImageView) view.findViewById(R.id.iv_home_grid_item);
			tv_home_grid_item.setText(names[position]);
			iv_home_grid_item.setImageResource(ids[position]);
			return view;
		}
	}

}
