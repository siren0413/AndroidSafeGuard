package com.yijun.androidsafeguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AntiTheftSetup1Activity extends Activity {

	private static final String TAG = "AntiTheftSetup1Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_theft_setup1);
	}

	public void next(View view){
		Intent intent = new Intent(this, AntiTheftSetup2Activity.class);
		startActivity(intent);
		Log.i(TAG, "Enter Anti-theft setup 2 activity");
	}
	
	public void previous(View view){
		Log.i(TAG, "Roll back to Anti-theft setup 1 activity");
		finish();
	}

}
