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
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "operator")
@Table(name = "SYS_Operator")
public class Operator extends BaseModel {
	
	public Operator() {}
	
	public Operator(HttpServletRequest request, String tableId) {
		super(request, tableId);
	}
	
	private static final long serialVersionUID = 1L;
	
	// 人员编码
	@Column(length = 32, unique = true, nullable = false)
	private String code;
	
	// 登陆名称
	@Column(length = 32, unique = true, nullable = false)
	private String loginName;
	
	// 真实姓名
	@Column(length = 20, nullable = false)
	private String trueName;
	
	// 性别
	@Column(length = 5)
	private String sex;
	
	// 职务编号
	@Column(length = 32, nullable = false)
	private String postCode;
	
	// 出生日期
	@Column(length = 20)
	private String brithday;
	
	// 身份证编号
	@Column(length = 25)
	private String idCard;
	
	// 密码
	@Column(length = 32, nullable = false)
	private String password;
	
	@Transient
	private String repassword;
	
	// 地址
	@Column(length = 500)
	private String address;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String models;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String roles;
	
	// 人员介绍
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String scription;
	
	// 联系电话
	@Column(length = 20)
	private String phone;
	
	// 手机
	@Column(length = 20)
	private String mobile;
	
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
	
	// 人员状态
	@Column
	private Integer status;
	
	// 主题
	@Column
	private String cssStyle;
	
	// 排序
	@Column
	private Integer sort;
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getTrueName() {
		return trueName;
	}
	
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getBrithday() {
		return brithday;
	}
	
	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}
	
	public String getIdCard() {
		return idCard;
	}
	
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepassword() {
		return repassword;
	}
	
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getModels() {
		return models;
	}
	
	public void setModels(String models) {
		this.models = models;
	}
	
	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getScription() {
		return scription;
	}
	
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getCssStyle() {
		return cssStyle;
	}
	
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
