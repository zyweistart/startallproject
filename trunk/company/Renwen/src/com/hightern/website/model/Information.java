/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "information")
@Table(name = "WS_Information")
public class Information extends BaseModel {
	
    public Information() {
    }

    public Information(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;
    //菜单编号
	private Long menuId;
    //菜单名称
    @Column
    private String menuname;
    
    private Integer pagetype;
    //标题
    @Column
    private String title;
    //内容
    @Lob
    private String content;
    //点击率
    private Integer hits;
    //是否发布(1未发布2已发布)
    private Integer releases;
    //创建时间
    @Column(length=19)
    private String createTime;
    //发布时间
    @Column(length=19)
    private String releaseTime;
    //创建者编号
    @Column
    private String createUserCode;
    //创建者姓名 
    @Column
    private String createUserName;
    //发布者编号
    @Column
    private String releaseUserCode;
    //发布者名称
    @Column
    private String releaseUserName;
    //缩略图路径
    @Column
    private String scalepic; 
    //附件路径
    @Column
    private String annexpath;
    
    private Integer top;
    
    private String fileName;
    
    private String contentType;
    
    private Integer photo;
    
    private String thumbnail;
    @Column
    private Integer sort;
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
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

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getReleases() {
		return releases;
	}

	public void setReleases(Integer releases) {
		this.releases = releases;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getReleaseUserCode() {
		return releaseUserCode;
	}

	public void setReleaseUserCode(String releaseUserCode) {
		this.releaseUserCode = releaseUserCode;
	}

	public String getReleaseUserName() {
		return releaseUserName;
	}

	public void setReleaseUserName(String releaseUserName) {
		this.releaseUserName = releaseUserName;
	}

	public String getScalepic() {
		return scalepic;
	}

	public void setScalepic(String scalepic) {
		this.scalepic = scalepic;
	}

	public String getAnnexpath() {
		return annexpath;
	}

	public void setAnnexpath(String annexpath) {
		this.annexpath = annexpath;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getPagetype() {
		return pagetype;
	}

	public void setPagetype(Integer pagetype) {
		this.pagetype = pagetype;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getPhoto() {
		return photo;
	}

	public void setPhoto(Integer photo) {
		this.photo = photo;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}