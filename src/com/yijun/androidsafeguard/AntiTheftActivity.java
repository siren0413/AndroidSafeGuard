package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.ui.ItemViewWithCheckbox;
import com.yijun.androidsafeguard.ui.ItemViewWithTextview;
import com.yijun.androidsafeguard.utils.Constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AntiTheftActivity extends Activity {
	
	private SharedPreferences sp;
	private ItemViewWithTextview ui_ivwt_setting_item;
	private ItemViewWithTextview ui_ivwt_antitheft_reset_item;
	private ItemViewWithCheckbox ui_ivwc_antitheft_item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences(Constant.PREF_FILE_NAME, MODE_PRIVATE);
		
		// setting guide
		boolean setup = sp.getBoolean(Constant.ANTI_THEFT_SETUP, false);
		if(setup){
			setContentView(R.layout.activity_anti_theft);
			ui_ivwt_setting_item = (ItemViewWithTextview)findViewById(R.id.ui_ivwt_setting_item);
			ui_ivwt_antitheft_reset_item = (ItemViewWithTextview)findViewById(R.id.ui_ivwt_antitheft_reset_item);
			ui_ivwc_antitheft_item = (ItemViewWithCheckbox) findViewById(R.id.ui_ivwc_antitheft_item);
			
			ui_ivwt_antitheft_reset_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new Builder(AntiTheftActivity.this);
					builder.setTitle("Reset");
					builder.setMessage("Are you sure to reset all settings?");
					builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Editor editor = sp.edit();
							editor.remove(Constant.ANTI_THEFT_SETUP);
							editor.remove(Constant.ANTI_THEFT_BIND_SIM);
							editor.remove(Constant.ANTI_THEFT_ON);
							editor.remove(Constant.ANTI_THEFT_PASSWORD);
							editor.remove(Constant.ANTI_THEFT_SECURE_PHONE);
							editor.remove(Constant.ANTI_THEFT_SIM_SERIAL_NUMBER);
							editor.commit();
							dialog.dismiss();
							AlertDialog.Builder builder2 = new Builder(AntiTheftActivity.this);
							builder2.setTitle("Success");
							builder2.setMessage("All settings has been reset");
							builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									AntiTheftActivity.this.finish();
								}
							});
							builder2.show();
						}
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.show();
				}
			});
			
			ui_ivwc_antitheft_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Editor editor = sp.edit();
					if(ui_ivwc_antitheft_item.isChecked()){
						ui_ivwc_antitheft_item.setChecked(false);
						ui_ivwc_antitheft_item.setStatus(false);
						editor.putBoolean(Constant.ANTI_THEFT_ON, false);
					}else{
						ui_ivwc_antitheft_item.setChecked(true);
						ui_ivwc_antitheft_item.setStatus(true);
						editor.putBoolean(Constant.ANTI_THEFT_ON, true);
					}
					editor.commit();
				}
			});
			ui_ivwt_setting_item.setText(sp.getString(Constant.ANTI_THEFT_SECURE_PHONE, ""));
			ui_ivwc_antitheft_item.setChecked(sp.getBoolean(Constant.ANTI_THEFT_ON, false));
			ui_ivwc_antitheft_item.setStatus(sp.getBoolean(Constant.ANTI_THEFT_ON, false));
		}else{
			Intent intent = new Intent(this, AntiTheftSetup1Activity.class);
			startActivity(intent);
			finish();
		}
	}


}
