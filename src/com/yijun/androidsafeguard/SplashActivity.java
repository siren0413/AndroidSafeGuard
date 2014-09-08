package com.yijun.androidsafeguard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import com.yijun.androidsafeguard.model.UpdateInfo;
import com.yijun.androidsafeguard.utils.StreamTools;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	
	private static final long SLEEP_TIME = 1000;

	private TextView tv_splash_version;
	private TextView tv_splash_update_progress;

	private UpdateInfo updateInfo;
	private Handler handler;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initial widgets
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_update_progress = (TextView) findViewById(R.id.tv_splash_update_progress);
		handler = new MessageHandler();
		sp = getSharedPreferences("config", MODE_PRIVATE);

		// set version
		tv_splash_version.append(getVersionName());

		// check update
		if(sp.getBoolean("update", true))
			checkUpdate();
		else{
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					enterHome();
				}
			}, SLEEP_TIME);
		}

		// animation
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(2);
		findViewById(R.id.rl_splash_root).startAnimation(aa);
	}

	private void checkUpdate() {
		new Thread() {
			public void run() {
				long startTime = System.currentTimeMillis();
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
						Log.i(TAG, "current apk version:" + getVersionName() + " server apk version:" + version);
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
					long endTime = System.currentTimeMillis();
					try {
						Thread.sleep(SLEEP_TIME - (endTime - startTime));
					} catch (InterruptedException e) {
						Log.e(TAG, e.getMessage());
					}
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

	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Update");
		builder.setMessage(updateInfo.getDescription());
		//builder.setCancelable(false);
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					// SD mounted
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(updateInfo.getApkUrl(), Environment.getExternalStorageDirectory().getAbsolutePath()
							+ "/AndroidSafeGuard.apk", new AjaxCallBack<File>() {

								@Override
								public void onFailure(Throwable t, int errorNo, String strMsg) {
									Log.e(TAG, t.getMessage());
									Toast.makeText(getApplicationContext(), "download error", Toast.LENGTH_LONG).show();
									super.onFailure(t, errorNo, strMsg);
								}

								@Override
								public void onLoading(long count, long current) {
									super.onLoading(count, current);
									int progress = (int) (current * 100 / count);
									tv_splash_update_progress.setText("downloaded:" + progress +"%");
								}

								@Override
								public void onSuccess(File t) {
									super.onSuccess(t);
									installApk(t);
								}

								private void installApk(File file) {
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
									startActivity(intent);
								}
					});
				} else {
					Toast.makeText(getApplicationContext(), "No SD card found.", Toast.LENGTH_LONG).show();
					enterHome();
				}
			}
		});
		builder.setNegativeButton("later", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
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
				showUpdateDialog();
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
