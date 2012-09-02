/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "users")
@Table(name = "SCH_Users")
public class Users extends BaseModel {
    public Users() {
    }

    public Users(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //用户名
    @Column
    private String userName;
    
    //密码
    @Column
    private String password;
    
    
    //状态
    @Column
    private Integer status;
    
    //姓名
    @Column
    private String uname;
    
    //身份证
    @Column
    private String identity;
    
    //班级
    @Column
    private String userCls;
    
    //任课教师
    @Column
    private String userTeacther;
    
    //某学期  1:第一学期  2：第二学期
    @Column
    private  Integer userXq; 
    
    //年份
     @Column
     private String userYear;
    
    //头像
    @Column
    private String avatar;
    
    //注册日期
    @Column
    private String startDay;
    
    //visits编号
    @Transient
    private Long visitsId;
    
    
    //进入测试库的开始时间
    @Transient
    private String extractionStartDay;
    
    
	//总访问量
    @Transient
	private String visitsMax;
    
    //总访问次数
    @Transient
    private String visitsSize;
    
	//测试库访问量
    @Transient
	private String visitsExtmMax;
    
	//测试次数
    @Transient
	private String visitsExtmSize;
	 
    
    
    
    
    
    
    public String getUserCls() {
		return userCls;
	}

	public void setUserCls(String userCls) {
		this.userCls = userCls;
	}

	public String getUserTeacther() {
		return userTeacther;
	}

	public void setUserTeacther(String userTeacther) {
		this.userTeacther = userTeacther;
	}

	public Integer getUserXq() {
		return userXq;
	}

	public void setUserXq(Integer userXq) {
		this.userXq = userXq;
	}

	public String getUserYear() {
		return userYear;
	}

	public void setUserYear(String userYear) {
		this.userYear = userYear;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
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

	public String getExtractionStartDay() {
		return extractionStartDay;
	}

	public void setExtractionStartDay(String extractionStartDay) {
		this.extractionStartDay = extractionStartDay;
	}

	public Long getVisitsId() {
		return visitsId;
	}

	public void setVisitsId(Long visitsId) {
		this.visitsId = visitsId;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	//排序
    @Column
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}