package com.yijun.androidsafeguard.db;

import java.util.List;
import java.util.Random;

import android.test.AndroidTestCase;

import com.yijun.androidsafeguard.db.dao.AntiSpamDAO;
import com.yijun.androidsafeguard.model.AntiSpamInfo;
import com.yijun.androidsafeguard.utils.Constant;

public class AntiSpamDAOTest extends AndroidTestCase {
 
	public void testInsert() {
		AntiSpamDAO dao = new AntiSpamDAO(getContext());
		dao.insert("110", Constant.ANTI_SPAM_MODE_PHONE);
	}

	public void testfind() throws Exception {
		AntiSpamDAO dao = new AntiSpamDAO(getContext());
		boolean result = dao.findExist("110");
		assertEquals(true, result);
	}

	public void testUpdate() throws Exception {
		AntiSpamDAO dao = new AntiSpamDAO(getContext());
		dao.update("110", Constant.ANTI_SPAM_MODE_ALL);
	}

	public void testDelete() throws Exception {
		AntiSpamDAO dao = new AntiSpamDAO(getContext());
		dao.delete("110");
	}
	
	public void testInsertBatch(){
		AntiSpamDAO dao = new AntiSpamDAO(getContext());
		long base = 13700000000L;
		for(int i = 0; i < 100; i++){
			dao.insert(String.valueOf(base+i), String.valueOf(new Random().nextInt(3)));
		}
	}
	
	public void testFindAll(){
		AntiSpamDAO dao = new AntiSpamDAO(getContext());
		List<AntiSpamInfo> list = dao.findAll();
		System.out.println(list.size());
		for(AntiSpamInfo info: list){
			System.out.println("number:"+info.getNumber()+" mode:"+info.getMode());
		}
	}

}
