/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "fbreply")
@Table(name = "SCH_Fbreply")
public class Fbreply extends BaseModel {
    public Fbreply() {
    }

    public Fbreply(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //回复内容
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String rcontent;
    
    //帖子ID
    @Column
    private Long potsId;
    
    //帖子标题
    @Column
    private String potsTitle;
    
    //用户ID
    @Column
    private Long userId;
    
    //用户名
    @Column
    private String userName;
    
    //头像
    @Column
    private String avatar;
    
    //回复时间
    @Column
    private String startDay;
    

    


	public String getRcontent() {
		return rcontent;
	}

	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}

	public Long getPotsId() {
		return potsId;
	}

	public void setPotsId(Long potsId) {
		this.potsId = potsId;
	}

	public String getPotsTitle() {
		return potsTitle;
	}

	public void setPotsTitle(String potsTitle) {
		this.potsTitle = potsTitle;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
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