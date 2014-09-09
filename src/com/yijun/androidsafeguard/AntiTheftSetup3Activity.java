package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.utils.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AntiTheftSetup3Activity extends AntiTheftBaseSetupActivity {

	private static final String TAG = "AntiTheftSetup3Activity";
	
	private EditText et_antitheft_setup3_phone_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_theft_setup3);
		et_antitheft_setup3_phone_number = (EditText) findViewById(R.id.et_antitheft_setup3_phone_number);
		String phone = sp.getString(Constant.ANTI_THEFT_SECURE_PHONE, "");
		et_antitheft_setup3_phone_number.setText(phone);
	}
	
	public void selectContact(View view){
		Intent intent = new Intent(this, SelectContactActivity.class);
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null) return;
		String phone = data.getStringExtra("phone");
		et_antitheft_setup3_phone_number.setText(phone);
	}
	
	public void next(View view){
		String phone = et_antitheft_setup3_phone_number.getText().toString().trim();
		if(!phone.isEmpty()){
			Editor editor = sp.edit();
			editor.putString(Constant.ANTI_THEFT_SECURE_PHONE, phone);
			editor.commit();
		}else{
			Toast.makeText(this, "phone number is empty", Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent(this, AntiTheftSetup4Activity.class);
		startActivity(intent);
		Log.i(TAG, "Enter Anti-theft setup 4 activity");
		finish();
		overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
	}
	
	public void previous(View view){
		Intent intent = new Intent(this, AntiTheftSetup2Activity.class);
		startActivity(intent);
		Log.i(TAG, "Roll back to Anti-theft setup 2 activity");
		finish();
		overridePendingTransition(R.anim.rev_trans_in, R.anim.rev_trans_out);
	}


}
