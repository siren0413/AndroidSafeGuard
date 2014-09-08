package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.ui.ItemViewWithCheckbox;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	private ItemViewWithCheckbox ui_ivwc_setting_item;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		ui_ivwc_setting_item = (ItemViewWithCheckbox) findViewById(R.id.ui_ivwc_setting_item);
		
		ui_ivwc_setting_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				
				if(ui_ivwc_setting_item.isChecked()){
					ui_ivwc_setting_item.setChecked(false);
					ui_ivwc_setting_item.setStatus(false);
					editor.putBoolean("update", false);
				}else{
					ui_ivwc_setting_item.setChecked(true);
					ui_ivwc_setting_item.setStatus(true);
					editor.putBoolean("update", true);
				}
				
				editor.commit();
			}
		});
		
		ui_ivwc_setting_item.setChecked(sp.getBoolean("update", false));
		ui_ivwc_setting_item.setStatus(sp.getBoolean("update", false));
		
	}

}
