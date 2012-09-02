/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "menu")
@Table(name = "WS_Menu")
public class Menu extends BaseModel {
    public Menu() {
    }

    public Menu(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;
    /**
     * 菜单名称
     */
    @Column(length = 100)
    private String name;
    /**
     * 页面类型:父级类型、单页面、列表页面、图文页面
     */
    private Integer pagetype;
    /**
     * 上级菜单的编号,顶级为空
     */
    private Long pid;
    
    @Column(length = 100)
    private String pname;
    /**
     * 级别 主级别为0以次类推
     */
    private Integer level;
    
    @Column
    private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getPagetype() {
		return pagetype;
	}

	public void setPagetype(Integer pagetype) {
		this.pagetype = pagetype;
	}

	public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}