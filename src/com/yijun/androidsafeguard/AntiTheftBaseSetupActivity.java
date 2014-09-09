package com.yijun.androidsafeguard;

import com.yijun.androidsafeguard.utils.Constant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public abstract class AntiTheftBaseSetupActivity extends Activity {
	
	protected GestureDetector detector;
	protected SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences(Constant.PREF_FILE_NAME, MODE_PRIVATE);
		detector = new GestureDetector(this, new SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				
				if(Math.abs(e1.getRawY() - e2.getRawY()) > 100) return true;
				
				if(Math.abs(velocityX) < 100) return true;
				
				if((e2.getRawX()- e1.getRawX()) > 120){
					previous(null);
					return true;
				}else if( (e1.getRawX() - e2.getRawX()) > 200){
					next(null);
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}
	
	public abstract void next(View view);
	public abstract void previous(View view);
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
