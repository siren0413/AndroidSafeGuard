package com.yijun.androidsafeguard.model;

public class UpdateInfo {
	private String version;
	private String description;
	private String apkUrl;
	
	public UpdateInfo() {
		super();
	}

	public UpdateInfo(String version, String description, String apkUrl) {
		super();
		this.version = version;
		this.description = description;
		this.apkUrl = apkUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	
}
