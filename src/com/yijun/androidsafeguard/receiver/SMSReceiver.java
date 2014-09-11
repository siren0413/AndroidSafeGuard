package com.yijun.androidsafeguard.receiver;

import com.yijun.androidsafeguard.db.dao.AntiSpamDAO;
import com.yijun.androidsafeguard.model.AntiSpamInfo;
import com.yijun.androidsafeguard.utils.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	private AntiSpamDAO dao;
	private final SmsManager sm = SmsManager.getDefault();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(dao == null){
			dao = new AntiSpamDAO(context);
		}
		final Bundle bundle = intent.getExtras();
		
		try {
			final Object[] pdusObj = (Object[])bundle.get("pdus");
			for (int i = 0; i < pdusObj.length; i++) {
                
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                AntiSpamInfo info = dao.find(phoneNumber);
                if(info == null) return;
                if(info.getMode().equals( Constant.ANTI_SPAM_MODE_SMS) || info.getMode().equals(Constant.ANTI_SPAM_MODE_ALL)){
                	Log.i(TAG, "incoming number: "+phoneNumber +" is in blacklists, block the SMS");
                	abortBroadcast();
                	return;
                }
                //String message = currentMessage.getDisplayMessageBody();
            } 
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
