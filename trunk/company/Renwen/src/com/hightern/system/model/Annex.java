/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "annex")
@Table(name = "SYS_Annex")
public class Annex extends BaseModel {
	
	public Annex() {}
	
	public Annex(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// 附件名称
	@Column(length = 500, nullable = false)
	private String annexName;
	
	// 附件地址
	@Column(length = 500, nullable = false)
	private String annexPath;
	
	// 附件说明
	@Column(length = 2000)
	private String annexRemark;
	
	// 附件类型
	@Column(length = 500, nullable = false)
	private String annexType;
	
	// 状态
	@Column
	private Integer status;
	
	// 排序
	@Column
	private Integer sort;
	
	public String getAnnexName() {
		return annexName;
	}
	
	public void setAnnexName(String annexName) {
		this.annexName = annexName;
	}
	
	public String getAnnexPath() {
		return annexPath;
	}
	
	public void setAnnexPath(String annexPath) {
		this.annexPath = annexPath;
	}
	
	public String getAnnexRemark() {
		return annexRemark;
	}
	
	public void setAnnexRemark(String annexRemark) {
		this.annexRemark = annexRemark;
	}
	
	public String getAnnexType() {
		return annexType;
	}
	
	public void setAnnexType(String annexType) {
		this.annexType = annexType;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}