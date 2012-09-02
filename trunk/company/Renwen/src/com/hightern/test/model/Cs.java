/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.test.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

//数据库备份(占时MYSQL)
@Entity(name = "cs")
@Table(name = "BFSQL_Cs")
public class Cs extends BaseModel {
    public Cs() {
    }

    public Cs(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //数据库备份名字
    @Column 
    private String title;  
    //数据库文件存放路径
    @Column
    private String filepath;
    //备注
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String content;
    //备份日期
    @Column
    private String startDay;
    //备份人登记
    @Column
    private String csname;
    //状态
    @Column
    private String  stort;
    
    
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getCsname() {
		return csname;
	}

	public void setCsname(String csname) {
		this.csname = csname;
	}


	public String getStort() {
		return stort;
	}

	public void setStort(String stort) {
		this.stort = stort;
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