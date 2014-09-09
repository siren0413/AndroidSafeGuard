package com.yijun.androidsafeguard.receiver;

import com.yijun.androidsafeguard.utils.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootCompleteReceiver extends BroadcastReceiver {

	SharedPreferences sp = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(sp == null)
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		// check SIM
		if(sp.getBoolean(Constant.ANTI_THEFT_BIND_SIM, false)){
			String simSerialNumber = sp.getString(Constant.ANTI_THEFT_SIM_SERIAL_NUMBER, null);
		}
	}

}
