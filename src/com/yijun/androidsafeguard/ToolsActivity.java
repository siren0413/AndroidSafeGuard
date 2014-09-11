package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.model.Locale;
import com.yijun.androidsafeguard.ui.ItemViewWithTextview;
import com.yijun.androidsafeguard.webservice.PhoneNumberLocaleWebservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ToolsActivity extends Activity {

	private ItemViewWithTextview ui_ivwt_tools_item;
	private EditText et_tools_phone_locale;
	private TextView tv_tools_phone_locale;
	private Button bn_tools_phone_locale_query;
	private Button bn_tools_phone_locale_cancel;
	
	private Vibrator vibrator;

	private AlertDialog phoneLocaleDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		ui_ivwt_tools_item = (ItemViewWithTextview) findViewById(R.id.ui_ivwt_tools_item);

	}

	public void phoneLocaleClick(View view) {
		View dialog = View.inflate(this, R.layout.tools_phone_locale_dialog, null);
		et_tools_phone_locale = (EditText) dialog.findViewById(R.id.et_tools_phone_locale);
		tv_tools_phone_locale = (TextView) dialog.findViewById(R.id.tv_tools_phone_locale);
		bn_tools_phone_locale_query = (Button) dialog.findViewById(R.id.bn_tools_phone_locale_query);
		bn_tools_phone_locale_cancel = (Button) dialog.findViewById(R.id.bn_tools_phone_locale_cancel);
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

		bn_tools_phone_locale_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (phoneLocaleDialog == null)
					return;
				phoneLocaleDialog.dismiss();
			}
		});

		bn_tools_phone_locale_query.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNum = et_tools_phone_locale.getText().toString().trim();
				if (phoneNum == null || phoneNum.isEmpty()) {
					Toast.makeText(ToolsActivity.this, "Phone number is empty", Toast.LENGTH_SHORT).show();
					vibrator.vibrate(500);
					return;
				}
				if (phoneNum.length() < 10) {
					Toast.makeText(ToolsActivity.this, "phone number format not correct", Toast.LENGTH_SHORT).show();
					vibrator.vibrate(500);
					return;
				}

				PhoneNumberLocaleWebservice service = new PhoneNumberLocaleWebservice();
				Locale locale = service.query(phoneNum.substring(0, 3));
				if (locale == null) {
					tv_tools_phone_locale.setText("Error");
				} else {
					tv_tools_phone_locale.setText(locale.getCity() + "." + locale.getState() + "." + locale.getZip());
				}
			}
		});

		AlertDialog.Builder builder = new Builder(this);
		phoneLocaleDialog = builder.create();
		phoneLocaleDialog.setView(dialog);
		phoneLocaleDialog.show();
	}

}
