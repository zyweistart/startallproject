/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;
//前台菜单
@Entity(name = "newstype")
@Table(name = "C_Newstype")
public class Newstype extends BaseModel {
    public Newstype() {
    }

    public Newstype(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    
    //TODO:在此添加相关字段属性

    //类型名称
    @Column
    private String name;
    
    //上级ID
    @Column
    private Long pid;
    
    //上级名称
    @Column
    private String pname;
    
    //几级类型
    @Column
    private Integer length;
    
    //子类
    @Transient
    private List<Newstype> children;
    
    //显示类型
	@Column(length=20)
	private Long category;
    
	//模板描述名
	@Column
	private String templateTitle;
	
	//图片
	@Column
	private String xcImg;
	
	//连接地址(点击图片的超链接)
	@Column
	private String imgUrl;
	
	
	//连接地址(点击菜单的超链接)
	@Column
	private String ljUrl;
	
	//状态是否为菜单目录(1：否  99：是)
	@Column
	private Integer tstart;
    
	
	//该菜单 客户是否可以添加子菜单  (1:不可以  2：可以)
	@Column
	private Integer operatStatus;
	
	//用户判断是否可以删除数据(即用户创建)(1:不可以 2:可以)
	@Column
	private Integer removeStatus;
	
	 //是否需要上传状态
    @Column
    private Integer upStatus;
    
    
    
	public Integer getUpStatus() {
		return upStatus;
	}

	public void setUpStatus(Integer upStatus) {
		this.upStatus = upStatus;
	}

	public Integer getOperatStatus() {
		return operatStatus;
	}

	public void setOperatStatus(Integer operatStatus) {
		this.operatStatus = operatStatus;
	}

	public Integer getRemoveStatus() {
		return removeStatus;
	}

	public void setRemoveStatus(Integer removeStatus) {
		this.removeStatus = removeStatus;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getXcImg() {
		return xcImg;
	}

	public void setXcImg(String xcImg) {
		this.xcImg = xcImg;
	}

	public String getLjUrl() {
		return ljUrl;
	}

	public void setLjUrl(String ljUrl) {
		this.ljUrl = ljUrl;
	}

	public Integer getTstart() {
		return tstart;
	}

	public void setTstart(Integer tstart) {
		this.tstart = tstart;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public List<Newstype> getChildren() {
		return children;
	}

	public void setChildren(List<Newstype> children) {
		this.children = children;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	
}