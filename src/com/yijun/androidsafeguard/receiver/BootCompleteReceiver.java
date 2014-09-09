package com.yijun.androidsafeguard.receiver;

import com.yijun.androidsafeguard.utils.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BootCompleteReceiver extends BroadcastReceiver {

	SharedPreferences sp = null;
	TelephonyManager tm = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (sp == null)
			sp = context.getSharedPreferences(Constant.PREF_FILE_NAME, Context.MODE_PRIVATE);
		if (tm == null)
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		// check SIM
		if (sp.getBoolean(Constant.ANTI_THEFT_ON, false)) {
			String simSerialNumber = sp.getString(Constant.ANTI_THEFT_SIM_SERIAL_NUMBER, null);
			String currSimSerialNumber = tm.getSimSerialNumber();
			if (simSerialNumber == null || currSimSerialNumber == null || !simSerialNumber.equals(currSimSerialNumber)) {
				// send message to secure number
				String phone = sp.getString(Constant.ANTI_THEFT_SECURE_PHONE, null);
				if (phone != null) {
					SmsManager sm = SmsManager.getDefault();
					sm.sendTextMessage(phone, null, "Alert: SIM card on ["+phone+"] changed", null	, null);
				}
			}
		}
	}

}
