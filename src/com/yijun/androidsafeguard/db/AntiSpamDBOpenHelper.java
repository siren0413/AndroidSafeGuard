package com.yijun.androidsafeguard.db;

import com.yijun.androidsafeguard.utils.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AntiSpamDBOpenHelper extends SQLiteOpenHelper {

	public AntiSpamDBOpenHelper(Context context) {
		super(context, Constant.ANTI_SPAM_DB_NAME, null, 1);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Constant.ANTI_SPAM_DB_CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
