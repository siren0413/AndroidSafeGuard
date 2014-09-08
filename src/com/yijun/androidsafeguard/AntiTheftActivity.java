package com.yijun.androidsafeguard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AntiTheftActivity extends Activity {
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		// setting guide
		boolean setup = sp.getBoolean("antiTheftSetup", false);
		if(setup){
			setContentView(R.layout.activity_anti_theft);
		}else{
			Intent intent = new Intent(this, AntiTheftSetup1Activity.class);
			startActivity(intent);
			finish();
		}
	}


}
