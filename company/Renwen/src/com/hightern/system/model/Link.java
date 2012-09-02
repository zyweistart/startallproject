/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "link")
@Table(name = "SYS_Link")
public class Link extends BaseModel {
	public Link() {}
	
	public Link(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// TODO:在此添加相关字段属性
	
	// 连接的显示名称
	@Column(length = 100)
	private String name;
	
	// 连接的URL地址
	@Column(length = 100)
	private String url;
	
	//类别(1:分院连接,2:其他连接)
	@Column(length=10)
	private Integer type;
	
	//
	@Column(length = 10)
	private Integer isShow;
	
	// 排序
	@Column
	private Integer sort;
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getIsShow() {
		return isShow;
	}
	
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}