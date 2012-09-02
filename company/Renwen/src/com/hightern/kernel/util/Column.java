package com.hightern.kernel.util;

public class Column {
	
	private String name;// 字段名
	private boolean nullalbe;// 能否为空
	private String javaType; // java类型
	private int length; // 长度
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isNullalbe() {
		return nullalbe;
	}
	
	public void setNullalbe(boolean nullalbe) {
		this.nullalbe = nullalbe;
	}
	
	public String getJavaType() {
		return javaType;
	}
	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
}
