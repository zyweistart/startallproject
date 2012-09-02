/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;
//新闻信息
@Entity(name = "integration")
@Table(name = "C_Integration")
public class Integration extends BaseModel {
    public Integration() {
    }

    public Integration(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //名称
    @Column(length=200)
    private String name;
    
	//内容
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String content;
    
	//来源
	@Column(length=200)
	private String newsfrom;
	
	//发布时间
	@Column(length = 50)
	private String issuedDay;

	//是否已发布(1:否  99：是)
	@Column
	private Integer isSued;
	
	//拟稿时间(更新时间)
	@Column(length = 50)
	private String startDay;
	
	//图片
	@Column(length=200)
	private String img;
	
	//菜单目录ID
	@Column(length = 50)
	private Long newsTypeId;
	
	// 父菜单目录ID
	@Column(length = 50)
	private Long parentNewsTypeId;

	// 菜单目录名称
	@Column(length = 200)
	private String newsTypeName;
	
	//显示形式
	@Column(length=20)
	private Long category;
	
	//下载文件路径
	@Column(length=200)
	private String filePath;
	
	//点击率
	@Column
	private Long hits;
	
	//首页宣传部分状态( 1-4状态 )
	@Column
	private Integer xcrj;
	
	
	
	//通知公告(0:否,99:是)
	@Column
	private Integer tzStart;
	
	//视频缩略图
	@Column
	private String videoPath;
	
	
	
	
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public Integer getTzStart() {
		return tzStart;
	}

	public void setTzStart(Integer tzStart) {
		this.tzStart = tzStart;
	}

	public Integer getXcrj() {
		return xcrj;
	}

	public void setXcrj(Integer xcrj) {
		this.xcrj = xcrj;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNewsfrom() {
		return newsfrom;
	}

	public void setNewsfrom(String newsfrom) {
		this.newsfrom = newsfrom;
	}

	public String getIssuedDay() {
		return issuedDay;
	}

	public void setIssuedDay(String issuedDay) {
		this.issuedDay = issuedDay;
	}

	public Integer getIsSued() {
		return isSued;
	}

	public void setIsSued(Integer isSued) {
		this.isSued = isSued;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}


	public Long getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(Long newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	public Long getParentNewsTypeId() {
		return parentNewsTypeId;
	}

	public void setParentNewsTypeId(Long parentNewsTypeId) {
		this.parentNewsTypeId = parentNewsTypeId;
	}

	public String getNewsTypeName() {
		return newsTypeName;
	}

	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
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