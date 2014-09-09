package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.ui.ItemViewWithCheckbox;
import com.yijun.androidsafeguard.utils.Constant;

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

public class AntiTheftSetup4Activity extends AntiTheftBaseSetupActivity {

	private static final String TAG = "AntiTheftSetup4Activity";
	private ItemViewWithCheckbox ui_ivwc_antitheft_setup4_item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_theft_setup4);
		
		ui_ivwc_antitheft_setup4_item = (ItemViewWithCheckbox)findViewById(R.id.ui_ivwc_antitheft_setup4_item);
		
		ui_ivwc_antitheft_setup4_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				
				if(ui_ivwc_antitheft_setup4_item.isChecked()){
					ui_ivwc_antitheft_setup4_item.setChecked(false);
					ui_ivwc_antitheft_setup4_item.setStatus(false);
					editor.putBoolean(Constant.ANTI_THEFT_ON, false);
					Log.i(TAG, "anti-theft ON");
				}else{
					ui_ivwc_antitheft_setup4_item.setChecked(true);
					ui_ivwc_antitheft_setup4_item.setStatus(true);
					editor.putBoolean(Constant.ANTI_THEFT_ON, true);
					Log.i(TAG, "anti-theft OFF");
				}
				
				editor.commit();
			}
		});
		
		ui_ivwc_antitheft_setup4_item.setChecked(sp.getBoolean(Constant.ANTI_THEFT_ON, false));
		ui_ivwc_antitheft_setup4_item.setStatus(sp.getBoolean(Constant.ANTI_THEFT_ON, false));
	}
	
	public void next(View view){
		Editor editor = sp.edit();
		editor.putBoolean(Constant.ANTI_THEFT_SETUP, true);
		editor.commit();
		
		Intent intent = new Intent(this, AntiTheftActivity.class);
		startActivity(intent);
		Log.i(TAG, "Enter Anti-theft Home activity");
		finish();
		overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
	}
	
	public void previous(View view){
		Intent intent = new Intent(this, AntiTheftSetup3Activity.class);
		startActivity(intent);
		Log.i(TAG, "Roll back to Anti-theft setup 3 activity");
		finish();
		overridePendingTransition(R.anim.rev_trans_in, R.anim.rev_trans_out);
	}


}
