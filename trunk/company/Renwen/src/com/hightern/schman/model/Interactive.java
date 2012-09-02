/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.model;
//知识互动
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "interactive")
@Table(name = "SCH_Interactive")
public class Interactive extends BaseModel {
    public Interactive() {
    }

    public Interactive(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //资源名称
    @Column
    private String ianame;
    
    //资源地址
    @Column
    private String filePath;
    
    //下载状态   (1:是  2:否)
    @Column 
    private Integer xzStrtus;
    
    //上传日期
    @Column
    private String startDay;
    
    
    //下载量
    @Column
    private String downqty;
    
    
    
    public String getDownqty() {
		return downqty;
	}

	public void setDownqty(String downqty) {
		this.downqty = downqty;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getIaname() {
		return ianame;
	}

	public void setIaname(String ianame) {
		this.ianame = ianame;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getXzStrtus() {
		return xzStrtus;
	}

	public void setXzStrtus(Integer xzStrtus) {
		this.xzStrtus = xzStrtus;
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