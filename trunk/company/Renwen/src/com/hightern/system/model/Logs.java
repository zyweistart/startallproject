/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "logs")
@Table(name = "SYS_Logs")
public class Logs extends BaseModel {
	public Logs() {}
	
	public Logs(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// 登陆名称
	@Column(length = 20, nullable = false)
	private String loginName;
	
	// 登陆时间
	@Column(length = 20)
	private String loginDate;
	
	// 登陆IP
	@Column(length = 20, nullable = false)
	private String loginIp;
	
	// 排序
	@Column
	private Integer sort;
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getLoginDate() {
		return loginDate;
	}
	
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getLoginIp() {
		return loginIp;
	}
	
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}