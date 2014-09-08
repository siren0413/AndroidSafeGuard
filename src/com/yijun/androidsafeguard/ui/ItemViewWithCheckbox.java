package com.yijun.androidsafeguard.ui;

import com.yijun.androidsafeguard.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Customized Relative layout, with two TextView and one Checkbox in it.
 *  
 * @author Yijun Mao
 *
 */

public class ItemViewWithCheckbox extends RelativeLayout {
	
	private CheckBox cb_setting_item;
	private TextView tv_setting_item_status;
	private TextView tv_setting_item_title;
	
	private String title;
	private String statusOffText;
	private String statusOnText;
	

	public ItemViewWithCheckbox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs, defStyle);
	}

	public ItemViewWithCheckbox(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs, 0);
	}

	public ItemViewWithCheckbox(Context context) {
		super(context);
		initView(context, null, 0);
	}
	
	private void initView(Context context, AttributeSet attrs, int defStyle) {
		View view = View.inflate(context, R.layout.item_view_with_checkbox, ItemViewWithCheckbox.this);
		cb_setting_item = (CheckBox)view.findViewById(R.id.cb_setting_item);
		tv_setting_item_status = (TextView)view.findViewById(R.id.tv_setting_item_status);
		tv_setting_item_title = (TextView)view.findViewById(R.id.tv_setting_item_title);
		title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yijun.androidsafeguard", "title");
		statusOnText = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yijun.androidsafeguard", "statusOnText");
		statusOffText = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yijun.androidsafeguard", "statusOffText");
		cb_setting_item.setChecked(true);
		tv_setting_item_status.setText(statusOnText);
		tv_setting_item_title.setText(title);
	}
	
	public boolean isChecked(){
		return cb_setting_item.isChecked();
	}
	
	public void setChecked(boolean checked){
		cb_setting_item.setChecked(checked);
	}
	
	public void setStatus(boolean status){
		if(status){
			tv_setting_item_status.setText(statusOnText);
		}else
			tv_setting_item_status.setText(statusOffText);
	}
	
}
