package com.hightern.schman.model;

public class Visitsmag {

	//提示用户名
	private String userName;
	//总访问量
	private String visitsMax;
	//总访问次数
	private String visitsSize;
	//测试库访问量
	private String visitsExtmMax;
	//测试次数
	private String visitsExtmSize;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVisitsSize() {
		return visitsSize;
	}
	public void setVisitsSize(String visitsSize) {
		this.visitsSize = visitsSize;
	}
	public String getVisitsMax() {
		return visitsMax;
	}
	public void setVisitsMax(String visitsMax) {
		this.visitsMax = visitsMax;
	}
	public String getVisitsExtmMax() {
		return visitsExtmMax;
	}
	public void setVisitsExtmMax(String visitsExtmMax) {
		this.visitsExtmMax = visitsExtmMax;
	}
	public String getVisitsExtmSize() {
		return visitsExtmSize;
	}
	public void setVisitsExtmSize(String visitsExtmSize) {
		this.visitsExtmSize = visitsExtmSize;
	}
	
	
	
}
