package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.ui.ItemViewWithCheckbox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class AntiTheftSetup2Activity extends Activity {

	protected static final String TAG = "AntiTheftSetup2Activity";
	private ItemViewWithCheckbox ui_ivwc_antitheft_setup2_item;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_anti_theft_setup2);
		ui_ivwc_antitheft_setup2_item = (ItemViewWithCheckbox) findViewById(R.id.ui_ivwc_antitheft_setup2_item);
		ui_ivwc_antitheft_setup2_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				
				if(ui_ivwc_antitheft_setup2_item.isChecked()){
					ui_ivwc_antitheft_setup2_item.setChecked(false);
					ui_ivwc_antitheft_setup2_item.setStatus(false);
					editor.putBoolean("bindSIM", false);
					Log.i(TAG, "SIM binded");
				}else{
					ui_ivwc_antitheft_setup2_item.setChecked(true);
					ui_ivwc_antitheft_setup2_item.setStatus(true);
					editor.putBoolean("bindSIM", true);
					Log.i(TAG, "SIM unbinded");
				}
				
				editor.commit();
			}
		});
		
		ui_ivwc_antitheft_setup2_item.setChecked(sp.getBoolean("bindSIM", false));
		ui_ivwc_antitheft_setup2_item.setStatus(sp.getBoolean("bindSIM", false));
	}
	
	public void next(View view){
		Intent intent = new Intent(this, AntiTheftSetup3Activity.class);
		startActivity(intent);
		Log.i(TAG, "Enter Anti-theft setup 3 activity");
	}
	
	public void previous(View view){
		Log.i(TAG, "Roll back to Anti-theft setup 1 activity");
		finish();
	}


}
