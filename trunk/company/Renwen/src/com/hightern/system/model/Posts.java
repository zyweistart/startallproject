/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "posts")
@Table(name = "SYS_Posts")
public class Posts extends BaseModel {
	public Posts() {}
	
	public Posts(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// 职务编号
	@Column(length = 20, nullable = false)
	private String postCode;
	
	// 职务名称
	@Column(length = 200, nullable = false)
	private String postName;
	
	// 详细说明
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String scription;
	
	// 职务状态
	@Column
	private Integer status;
	
	// 拥有的菜单模块
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String models;
	
	// 创建人
	@Column(length = 20)
	private String createName;
	
	// 创建时间
	@Column(length = 20)
	private String createDate;
	
	// 修改人
	@Column(length = 20)
	private String modifyName;
	
	// 修改时间
	@Column(length = 20)
	private String modifyDate;
	
	// 排序
	@Column
	private Integer sort;
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getPostName() {
		return postName;
	}
	
	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	public String getScription() {
		return scription;
	}
	
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getModels() {
		return models;
	}
	
	public void setModels(String models) {
		this.models = models;
	}
	
	public String getCreateName() {
		return createName;
	}
	
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getModifyName() {
		return modifyName;
	}
	
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	
	public String getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}