package com.yijun.androidsafeguard.utils;

public class Constant {
	public static final String PREF_FILE_NAME = "config";
	
	// Anti-Theft
	public static final String ANTI_THEFT_SETUP = "antiTheftSetup";
	public static final String ANTI_THEFT_BIND_SIM = "bindSIM";
	public static final String ANTI_THEFT_ON = "anti-theft";
	public static final String ANTI_THEFT_PASSWORD = "password";
	public static final String ANTI_THEFT_SECURE_PHONE = "security_phone";
	public static final String ANTI_THEFT_SIM_SERIAL_NUMBER = "sim_serial_number";
	
	
	// Anti-Spam
	public static final String ANTI_SPAM_MODE_PHONE = "0";
	public static final String ANTI_SPAM_MODE_SMS = "1";
	public static final String ANTI_SPAM_MODE_ALL = "2";
	public static final String ANTI_SPAM_DB_NAME = "antispam.db";
	public static final String ANTI_SPAM_TABLE_NAME = "antispam";
	public static final String ANTI_SPAM_DB_CREATE_TABLE = "create table "+ANTI_SPAM_TABLE_NAME+" (_id integer primary key autoincrement, number varchar(20), mode verchar(2))";
	public static final String ANTI_SPAM_DB_SQL_FIND_NUMBER = "select number, mode from "+ANTI_SPAM_TABLE_NAME+" where number = ?";
	public static final String ANTI_SPAM_DB_SQL_FIND_ALL = "select number, mode from "+ANTI_SPAM_TABLE_NAME +" order by _id desc";
	
}
