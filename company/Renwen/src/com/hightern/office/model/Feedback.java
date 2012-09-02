/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.office.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;
//留言板
@Entity(name = "feedback")
@Table(name = "OFFICE_Feedback")
public class Feedback extends BaseModel {
    public Feedback() {
    }

    public Feedback(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //姓名
    @Column(length=100)
    private String name;
    
    //联系方式
    @Column
    private String contact;
    
    //电子邮件
    @Column
    private String email;
    
    //标题
    @Column
    private String title;
    
    //内容
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String content;
    
    //留言时间
    @Column
    private String startDay;
    
    
    
    public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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