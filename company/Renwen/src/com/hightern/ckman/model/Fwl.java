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

@Entity(name = "fwl")
@Table(name = "CK_Fwl")
public class Fwl extends BaseModel {
    public Fwl() {
    }

    public Fwl(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    
    
    
    //访问量
    @Column
    private Long ls;
    
    
	public Long getLs() {
		return ls;
	}

	public void setLs(Long ls) {
		this.ls = ls;
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