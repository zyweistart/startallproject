/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.ckman.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "gjz")
@Table(name = "CK_Gjz")
public class Gjz extends BaseModel {
    public Gjz() {
    }

    public Gjz(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性
   
    //网站名称
    @Column
    private String name;
    
    //联系方式
    @Column
    private String lxtitle;
    
    //软件开发公司
    @Column
    private String dcompany; 
    
    //关键字
    @Column
    private String  title;
    
    
    
    public String getDcompany() {
		return dcompany;
	}

	public void setDcompany(String dcompany) {
		this.dcompany = dcompany;
	}

	public String getLxtitle() {
		return lxtitle;
	}

	public void setLxtitle(String lxtitle) {
		this.lxtitle = lxtitle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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