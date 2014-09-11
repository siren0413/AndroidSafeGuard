package com.yijun.androidsafeguard.db.dao;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yijun.androidsafeguard.db.AntiSpamDBOpenHelper;
import com.yijun.androidsafeguard.model.AntiSpamInfo;
import com.yijun.androidsafeguard.utils.Constant;

public class AntiSpamDAO {
	private static final String TAG = "AntiSpamDAO";
	private AntiSpamDBOpenHelper helper;

	public AntiSpamDAO(Context context) {
		helper = new AntiSpamDBOpenHelper(context);
	}
	
	public boolean findExist(String number){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(Constant.ANTI_SPAM_DB_SQL_FIND_NUMBER, new String[]{number});
		if(cursor.moveToNext()){
			cursor.close();
			db.close();
			return true;
		}
		return false;
	}
	
	public AntiSpamInfo find(String number){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(Constant.ANTI_SPAM_DB_SQL_FIND_NUMBER, new String[]{number});
		Log.i(TAG, "exec query: "+ Constant.ANTI_SPAM_DB_SQL_FIND_NUMBER +" number=["+number+"]");
		AntiSpamInfo info;
		if(cursor.moveToNext()){
			info = new AntiSpamInfo();
			info.setNumber(cursor.getString(0));
			info.setMode(cursor.getString(1));
			cursor.close();
			db.close();
			return info;
		}
		return null;
	}
	
	public void insert(String number, String mode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		db.insert(Constant.ANTI_SPAM_TABLE_NAME, null, values);
		db.close();
	}
	
	public void update(String number, String newMode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", newMode);
		db.update(Constant.ANTI_SPAM_TABLE_NAME, values, "number=?", new String[]{number});
		db.close();
	}
	
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(Constant.ANTI_SPAM_TABLE_NAME, "number=?", new String[]{number});
		db.close();
	}
	
	public List<AntiSpamInfo> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(Constant.ANTI_SPAM_DB_SQL_FIND_ALL, null);
		List<AntiSpamInfo> list = new LinkedList<>();
		while(cursor.moveToNext()){
			AntiSpamInfo info = new AntiSpamInfo();
			info.setNumber(cursor.getString(0));
			info.setMode(cursor.getString(1));
			list.add(info);
		}
		cursor.close();
		db.close();
		return list;
	}
	
}
