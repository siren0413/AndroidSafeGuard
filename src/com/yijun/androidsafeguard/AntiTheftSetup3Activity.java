package com.yijun.androidsafeguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AntiTheftSetup3Activity extends AntiTheftBaseSetupActivity {

	private static final String TAG = "AntiTheftSetup3Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_theft_setup3);
	}
	
	public void next(View view){
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
