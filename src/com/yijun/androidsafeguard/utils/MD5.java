package com.yijun.androidsafeguard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/**
	 * encode message using MD5 algorithm.
	 * 
	 * @param message
	 * @return encoded message
	 * @throws NoSuchAlgorithmException
	 */
	public static String encode(String message) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("md5");
		byte [] result = digest.digest(message.getBytes());
		StringBuffer buffer  = new StringBuffer();
		for(byte b : result){
			int number =  b & 0xff;
			String str = Integer.toHexString(number);
			if(str.length()==1){
				buffer.append("0");
			}
			buffer.append(str);
		}
		return buffer.toString();
	}
}
