package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.ui.ItemViewWithCheckbox;
import com.yijun.androidsafeguard.utils.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class AntiTheftSetup2Activity extends AntiTheftBaseSetupActivity {

	protected static final String TAG = "AntiTheftSetup2Activity";
	private ItemViewWithCheckbox ui_ivwc_antitheft_setup2_item;
	private TelephonyManager tm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_theft_setup2);
		tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		ui_ivwc_antitheft_setup2_item = (ItemViewWithCheckbox) findViewById(R.id.ui_ivwc_antitheft_setup2_item);
		ui_ivwc_antitheft_setup2_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				
				if(ui_ivwc_antitheft_setup2_item.isChecked()){
					ui_ivwc_antitheft_setup2_item.setChecked(false);
					ui_ivwc_antitheft_setup2_item.setStatus(false);
					editor.putBoolean(Constant.ANTI_THEFT_BIND_SIM, false);
					Log.i(TAG, "SIM unbinded");
					editor.remove(Constant.ANTI_THEFT_SIM_SERIAL_NUMBER);
					Log.i(TAG, "SIM serial number cleaned");
				}else{
					ui_ivwc_antitheft_setup2_item.setChecked(true);
					ui_ivwc_antitheft_setup2_item.setStatus(true);
					editor.putBoolean(Constant.ANTI_THEFT_BIND_SIM, true);
					Log.i(TAG, "SIM binded");
					String simSerialNumber = tm.getSimSerialNumber();
					editor.putString(Constant.ANTI_THEFT_SIM_SERIAL_NUMBER, simSerialNumber);
					Log.i(TAG, "SIM serial number saved: " + simSerialNumber);
				}
				
				editor.commit();
			}
		});
		
		ui_ivwc_antitheft_setup2_item.setChecked(sp.getBoolean(Constant.ANTI_THEFT_BIND_SIM, false));
		ui_ivwc_antitheft_setup2_item.setStatus(sp.getBoolean(Constant.ANTI_THEFT_BIND_SIM, false));
	}
	
	public void next(View view){
		Intent intent = new Intent(this, AntiTheftSetup3Activity.class);
		startActivity(intent);
		Log.i(TAG, "Enter Anti-theft setup 3 activity");
		finish();
		overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
	}
	
	public void previous(View view){
		Intent intent = new Intent(this, AntiTheftSetup1Activity.class);
		startActivity(intent);
		Log.i(TAG, "Roll back to Anti-theft setup 1 activity");
		finish();
		overridePendingTransition(R.anim.rev_trans_in, R.anim.rev_trans_out);
	}


}
