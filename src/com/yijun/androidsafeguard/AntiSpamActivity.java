package com.yijun.androidsafeguard;

import java.util.List;

import com.yijun.androidsafeguard.db.dao.AntiSpamDAO;
import com.yijun.androidsafeguard.model.AntiSpamInfo;
import com.yijun.androidsafeguard.utils.Constant;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AntiSpamActivity extends Activity {

	private ListView lv_antispam;
	private Button bt_antispam_add;
	
	private List<AntiSpamInfo> list;
	private AntiSpamDAO dao;
	private AntiSpamInfoAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_spam);
		lv_antispam = (ListView) this.findViewById(R.id.lv_antispam);
		bt_antispam_add = (Button) this.findViewById(R.id.bt_antispam_add);
		dao = new AntiSpamDAO(this);
		list = dao.findAll();
		adapter = new AntiSpamInfoAdapter();
		lv_antispam.setAdapter(adapter);
	}
	
	public void addNumber(View view){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View v = View.inflate(this, R.layout.antispam_add_dialog, null);
		final EditText et_antispam_number = (EditText) v.findViewById(R.id.et_antispam_number);
		final CheckBox cb_antispam_phone = (CheckBox) v.findViewById(R.id.cb_antispam_phone);
		final CheckBox cb_antispam_sms = (CheckBox) v.findViewById(R.id.cb_antispam_sms);
		Button bt_antispam_yes = (Button)v.findViewById(R.id.bt_antispam_yes);
		Button bt_antispam_no = (Button) v.findViewById(R.id.bt_antispam_no);
		bt_antispam_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		bt_antispam_yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String number = et_antispam_number.getText().toString().trim();
				if(TextUtils.isEmpty(number)){
					Toast.makeText(AntiSpamActivity.this, "phone number is empty", Toast.LENGTH_SHORT).show();
					return;
				}
				AntiSpamInfo info;
				if(cb_antispam_phone.isChecked() && cb_antispam_sms.isChecked()){
					dao.insert(number, Constant.ANTI_SPAM_MODE_ALL);
					info = new AntiSpamInfo(number,Constant.ANTI_SPAM_MODE_ALL);
				}else if(cb_antispam_phone.isChecked()){
					dao.insert(number, Constant.ANTI_SPAM_MODE_PHONE);
					info = new AntiSpamInfo(number,Constant.ANTI_SPAM_MODE_PHONE);
				}else if(cb_antispam_sms.isChecked()){
					dao.insert(number, Constant.ANTI_SPAM_MODE_SMS);
					info = new AntiSpamInfo(number,Constant.ANTI_SPAM_MODE_SMS);
				}else{
					Toast.makeText(AntiSpamActivity.this, "Block mode is not selected", Toast.LENGTH_SHORT).show();
					return;
				}
				list.add(0, info);
				adapter.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
		
		
		dialog.setView(v);
		dialog.show();
	}

	private class AntiSpamInfoAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view;
			final TextView tv_antispam_number;
			TextView tv_antispam_mode;
			ImageView iv_antispam_item_delete;
			if(convertView == null){
				view = View.inflate(AntiSpamActivity.this, R.layout.item_view_with_button, null);
				tv_antispam_number = (TextView) view.findViewById(R.id.tv_antispam_number);
				tv_antispam_mode = (TextView) view.findViewById(R.id.tv_antispam_mode);
				iv_antispam_item_delete = (ImageView) view.findViewById(R.id.iv_antispam_item_delete);
				view.setTag(R.id.tv_antispam_number, tv_antispam_number);
				view.setTag(R.id.tv_antispam_mode, tv_antispam_mode);
				view.setTag(R.id.iv_antispam_item_delete, iv_antispam_item_delete);
				
				iv_antispam_item_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(AntiSpamActivity.this);
						builder.setTitle("Warning");
						builder.setMessage("Remove "+tv_antispam_number.getText().toString()+" from Blacklists?");
						builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dao.delete(tv_antispam_number.getText().toString());
								list.remove(position);
								adapter.notifyDataSetChanged();
								dialog.dismiss();
							}
						});
						builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						builder.show();
					}
				});
			}else{
				view = convertView;
				tv_antispam_number = (TextView)view.getTag(R.id.tv_antispam_number);
				tv_antispam_mode = (TextView)view.getTag(R.id.tv_antispam_mode);
				iv_antispam_item_delete = (ImageView)view.getTag(R.id.iv_antispam_item_delete);
			}
			
			AntiSpamInfo info = list.get(position);
			String mode = "";
			switch (info.getMode()) {
			case Constant.ANTI_SPAM_MODE_PHONE:
				mode = "PHONE BLOCK";
				break;
			case Constant.ANTI_SPAM_MODE_SMS:
				mode = "SMS BLOCK";
				break;
			case Constant.ANTI_SPAM_MODE_ALL:
				mode = "PHONE & SMS BLOCK";
				break;
			default:
				break;
			}
			tv_antispam_number.setText(info.getNumber());
			tv_antispam_mode.setText(mode);
			return view;
		}
		
	}
}
