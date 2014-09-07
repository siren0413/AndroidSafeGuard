package com.yijun.androidsafeguard;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.yijun.androidsafeguard.model.UpdateInfo;
import com.yijun.androidsafeguard.utils.StreamTools;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;

	private TextView tv_splash_version;

	private UpdateInfo updateInfo;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initial widgets
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		handler = new MessageHandler();

		// set version
		tv_splash_version.append(getVersionName());

		// check update
		checkUpdate();
	}

	private void checkUpdate() {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					URL url = new URL(getString(R.string.serverurl));
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code = conn.getResponseCode();
					// status 200: OK
					if (code == 200) {
						InputStream is = conn.getInputStream();
						String res = StreamTools.readFromStream(is);
						Log.i(TAG, "connected to update server. update info[" + res + "]");
						// parse json to get update info
						JSONObject obj = new JSONObject(res);
						String version = (String) obj.get("version");
						String description = (String) obj.get("description");
						String apkUrl = (String) obj.get("apkurl");
						updateInfo = new UpdateInfo(version, description, apkUrl);
						// validate if this is the new version
						Log.i(TAG, "current apk version:"+getVersionName()+" server apk version:"+version);
						if (getVersionName().trim().equals(updateInfo.getVersion().trim())) {
							// no new version. enter main window.
							msg.what = ENTER_HOME;
						} else {
							// new version exist. update apk.
							msg.what = SHOW_UPDATE_DIALOG;
						}
					}
				} catch (MalformedURLException e) {
					msg.what = URL_ERROR;
					Log.e(TAG, e.getMessage());
				} catch (IOException e) {
					msg.what = NETWORK_ERROR;
					Log.e(TAG, e.getMessage());
				} catch (JSONException e) {
					msg.what = JSON_ERROR;
					Log.e(TAG, e.getMessage());
				} finally {
					handler.sendMessage(msg);
				}
			};
		}.start();

	}

	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pInfo = pm.getPackageInfo(getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
			return "unknown";
		}
	}

	private void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private class MessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ENTER_HOME:
				enterHome();
				Log.i(TAG, "enter home activity.");
				break;
			case SHOW_UPDATE_DIALOG:
				Log.i(TAG, "show upate dialog.");
				break;
			case URL_ERROR:
				enterHome();
				break;
			case NETWORK_ERROR:
				enterHome();
				break;
			case JSON_ERROR:
				enterHome();
				break;
			default:
				break;
			}
		}

	}
}
