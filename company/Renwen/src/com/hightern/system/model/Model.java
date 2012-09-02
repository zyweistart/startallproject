/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.model;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "model")
@Table(name = "SYS_Model")
public class Model extends BaseModel {
	
	public Model() {}
	
	public Model(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// 菜单名称
	@Column(length = 200, nullable = false)
	private String name;
	
	// 上级菜单编号
	@Column
	private Long parentId;
	
	// 上级菜单名称
	@Column(length = 200)
	private String parentName;
	
	@Transient
	private Collection<Model> children;
	
	// 几级菜单
	@Column
	private Integer grade;
	
	// 访问地址
	@Column(length = 500)
	private String url;
	
	// 备注
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String scription;
	
	// 排序
	@Column
	private Integer sort;
	
	public Integer getGrade() {
		return grade;
	}
	
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getScription() {
		return scription;
	}
	
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Collection<Model> getChildren() {
		return children;
	}
	
	public void setChildren(Collection<Model> children) {
		this.children = children;
	}
	
}
