/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

//前台信息的模板页面 (即前台的信息对应一些页面)
@Entity(name = "templatetype")
@Table(name = "C_Templatetype")
public class Templatetype extends BaseModel {
    public Templatetype() {
    }

    public Templatetype(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //名称
    @Column
    private String title;
    
    //路径(模板页面路径)
    @Column
    private String mbPath;
    
    //模板效果图
    @Column
    private String filePath;
        
    //模板  用户可用不可用(1:可用 2:不可用)
    @Column
    private Integer operatStatus;
    
    //一页 数据量
    @Column
    private Integer dataSize;
    
    
    //是否需要上传
    @Column
    private Integer upStatus;
    
    
    
    
    
    
    public Integer getUpStatus() {
		return upStatus;
	}

	public void setUpStatus(Integer upStatus) {
		this.upStatus = upStatus;
	}

	public Integer getDataSize() {
		return dataSize;
	}

	public void setDataSize(Integer dataSize) {
		this.dataSize = dataSize;
	}

	public Integer getOperatStatus() {
		return operatStatus;
	}

	public void setOperatStatus(Integer operatStatus) {
		this.operatStatus = operatStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMbPath() {
		return mbPath;
	}

	public void setMbPath(String mbPath) {
		this.mbPath = mbPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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