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

@Entity(name = "role")
@Table(name = "SYS_Role")
public class Role extends BaseModel {
	
	public Role() {}
	
	public Role(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// 角色编码
	@Column(length = 32, nullable = false)
	private String roleCode;
	
	// 角色名称
	@Column(length = 100, nullable = false)
	private String roleName;
	
	// 角色说明
	@Column(length = 1000)
	private String scription;
	
	// 拥有的菜单编号
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
	
	// 职务状态
	@Column
	private Integer status;
	
	// 排序
	@Column
	private Integer sort;
	
	public String getModels() {
		return models;
	}
	
	public void setModels(String models) {
		this.models = models;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getScription() {
		return scription;
	}
	
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getCreateName() {
		return createName;
	}
	
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	public String getModifyName() {
		return modifyName;
	}
	
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
