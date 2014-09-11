package com.yijun.androidsafeguard.db;

import android.test.AndroidTestCase;

public class AntiSpamDBOpenHelperTest extends AndroidTestCase {
	
	public void testCreateDB() throws Exception{
		AntiSpamDBOpenHelper helper = new AntiSpamDBOpenHelper(getContext());
		helper.getWritableDatabase();
	}
}
