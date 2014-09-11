package com.yijun.androidsafeguard.receiver;

import com.yijun.androidsafeguard.db.dao.AntiSpamDAO;
import com.yijun.androidsafeguard.model.AntiSpamInfo;
import com.yijun.androidsafeguard.utils.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneCallReceiver extends BroadcastReceiver {

	protected static final String TAG = "PhoneCallReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		final AntiSpamDAO dao = new AntiSpamDAO(context);
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		tm.listen(new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				Log.i(TAG, "incoming call: "+incomingNumber);
				super.onCallStateChanged(state, incomingNumber);
				AntiSpamInfo info = dao.find(incomingNumber.trim());
				if(info == null) return;
				if(info.getMode().equals(Constant.ANTI_SPAM_MODE_ALL) || info.getMode().equals(Constant.ANTI_SPAM_MODE_PHONE)){
					Log.i(TAG, "incoming number: "+incomingNumber +" is in blacklists, block the call");
					abortBroadcast();
					return;
				}
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);
	}

}
