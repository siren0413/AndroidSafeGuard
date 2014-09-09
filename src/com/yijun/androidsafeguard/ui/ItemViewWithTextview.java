package com.yijun.androidsafeguard.ui;

import com.yijun.androidsafeguard.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemViewWithTextview extends RelativeLayout {
	
	private TextView tv_setting_item_title;
	private TextView tv_setting_item;
	
	private String title;

	public ItemViewWithTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs, defStyle);
	}

	public ItemViewWithTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs, 0);
	}

	public ItemViewWithTextview(Context context) {
		super(context);
		initView(context, null, 0);
	}

	private void initView(Context context, AttributeSet attrs, int i) {
		View view = View.inflate(context, R.layout.item_view_with_textview , ItemViewWithTextview.this);
		tv_setting_item_title = (TextView)view.findViewById(R.id.tv_setting_item_title);
		tv_setting_item = (TextView) view.findViewById(R.id.tv_setting_item);
		title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yijun.androidsafeguard", "title");
		tv_setting_item_title.setText(title);
	}
	
	public void setText(String s){
		tv_setting_item.setText(s);
	}
	
}
