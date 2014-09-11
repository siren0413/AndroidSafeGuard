package com.yijun.androidsafeguard;

import java.security.NoSuchAlgorithmException;

import com.yijun.androidsafeguard.utils.Constant;
import com.yijun.androidsafeguard.utils.MD5;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private static final String TAG = "HomeActivity"; 
	
	private GridView gv_home_body;
	
	private EditText et_antitheft_account_setup_password;
	private EditText et_antitheft_account_setup_confirm;
	private Button bt_antitheft_account_setup_set;
	private Button bt_antitheft_account_setup_cancel;
	
	private EditText et_antitheft_account_validate_password;
	private Button bt_antitheft_account_validate_ok;
	private Button bt_antitheft_account_validate_cancel;
	
	private AlertDialog antitheft_account_setup_dialog;
	private AlertDialog antitheft_account_validate_dialog;
	
	private SharedPreferences sp;

	
	
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
		sp = getSharedPreferences(Constant.PREF_FILE_NAME, MODE_PRIVATE);
		
		gv_home_body.setAdapter(new BodyAdapter());
		gv_home_body.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent;
				switch (position) {
				case ANTI_THEFT:
					showAntiTheftDialog();
					break;
				case SETTINGS:
					intent = new Intent(HomeActivity.this, SettingActivity.class);
					startActivity(intent);
					break;
				case TOOLS:
					intent = new Intent(HomeActivity.this, ToolsActivity.class);
					startActivity(intent);
					break;
				case ANTI_SPAM:
					intent = new Intent(HomeActivity.this, AntiSpamActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}
	
	
	protected void showAntiTheftDialog() {
		if(isSetupAntiTheftAccount()){
			showAntiTheftAccountValidateDialog();
		}else{
			showSetupAndiTheftAccountDialog();
		}
	}
	
	private void showSetupAndiTheftAccountDialog() {
		Log.i(TAG, "show anti-theft account setup dialog.");
		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.antitheft_setup_dialog, null);
		
		// initial widgets
		et_antitheft_account_setup_password = (EditText) view.findViewById(R.id.et_antitheft_account_setup_password);
		et_antitheft_account_setup_confirm = (EditText)view.findViewById(R.id.et_antitheft_account_setup_confirm);
		bt_antitheft_account_setup_set = (Button)view.findViewById(R.id.bt_antitheft_account_setup_set);
		bt_antitheft_account_setup_cancel = (Button) view.findViewById(R.id.bt_antitheft_account_setup_cancel);
		
		// setup listener
		bt_antitheft_account_setup_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				antitheft_account_setup_dialog.dismiss();
				Log.i(TAG, "anti-theft setup canceled");
			}
		});
		bt_antitheft_account_setup_set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String password = et_antitheft_account_setup_password.getText().toString().trim();
				String confirm = et_antitheft_account_setup_confirm.getText().toString().trim();
				if(!password.equals(confirm)){
					Toast.makeText(HomeActivity.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
					Log.i(TAG, "The passwords do not match");
					return;
				}
				if(password.isEmpty()){
					Toast.makeText(HomeActivity.this, "The password is empty", Toast.LENGTH_SHORT).show();
					Log.i(TAG, "The password is empty");
					return;
				}
				
				// save to config
				Editor editor = sp.edit();
				String encodedPwd = password;
				try {
					encodedPwd = MD5.encode(password);
				} catch (NoSuchAlgorithmException e) {
					Log.i(TAG, "MD5 algorithm does not exist");
					Toast.makeText(HomeActivity.this, "Internal Error.", Toast.LENGTH_SHORT).show();
				}
				editor.putString(Constant.ANTI_THEFT_PASSWORD, encodedPwd);
				editor.commit();
				Log.i(TAG, "Saved password to config");
				antitheft_account_setup_dialog.dismiss();
				
				Log.i(TAG, "Show anti-t heft account validate dialog");
				showAntiTheftAccountValidateDialog();
			}
		});
		antitheft_account_setup_dialog = builder.create();
		antitheft_account_setup_dialog.setView(view,0,0,0,0);
		antitheft_account_setup_dialog.show();
	}


	private void showAntiTheftAccountValidateDialog() {
		Log.i(TAG, "show anti-theft validate dialog.");
		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.antitheft_validate_dialog, null);
		
		// initial widgets
		et_antitheft_account_validate_password = (EditText) view.findViewById(R.id.et_antitheft_account_validate_password);
		bt_antitheft_account_validate_ok = (Button)view.findViewById(R.id.bt_antitheft_account_validate_ok);
		bt_antitheft_account_validate_cancel = (Button) view.findViewById(R.id.bt_antitheft_account_validate_cancel);
		
		bt_antitheft_account_validate_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				antitheft_account_validate_dialog.dismiss();
				Log.i(TAG, "anti-theft validate canceled");
			}
		});
		
		bt_antitheft_account_validate_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String password = et_antitheft_account_validate_password.getText().toString().trim();
				try {
					password = MD5.encode(password);
				} catch (NoSuchAlgorithmException e) {
					Log.i(TAG, "MD5 algorithm does not exist");
					Toast.makeText(HomeActivity.this, "Internal Error.", Toast.LENGTH_SHORT).show();
				}
				String sp_password = sp.getString(Constant.ANTI_THEFT_PASSWORD, null);
				if(password.isEmpty()){
					Toast.makeText(HomeActivity.this, "The password is empty", Toast.LENGTH_SHORT).show();
					Log.i(TAG, "The password is empty");
					return;
				}
				if(!password.equals(sp_password)){
					Toast.makeText(HomeActivity.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
					Log.i(TAG, "The passwords do not match");
					return;
				}
				Log.i(TAG, "The passwords is correct. prepare to enter anti-theft activity.");
				antitheft_account_validate_dialog.dismiss();
				enterAntiTheftActivity();
			}
		});
		antitheft_account_validate_dialog = builder.create();
		antitheft_account_validate_dialog.setView(view, 0, 0, 0, 0);
		antitheft_account_validate_dialog.show();
	}
	
	private void enterAntiTheftActivity() {
		Intent intent = new Intent(HomeActivity.this, AntiTheftActivity.class);
		startActivity(intent);
	}    


	private boolean isSetupAntiTheftAccount(){
		String password = sp.getString(Constant.ANTI_THEFT_PASSWORD, null);
		return !TextUtils.isEmpty(password);
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
