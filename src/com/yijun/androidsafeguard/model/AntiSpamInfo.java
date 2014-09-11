package com.yijun.androidsafeguard.model;

public class AntiSpamInfo {
	private String number;
	private String mode;
	
	public AntiSpamInfo(String number, String mode) {
		super();
		this.number = number;
		this.mode = mode;
	}
	public AntiSpamInfo() {
		super();
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	};
	
}
