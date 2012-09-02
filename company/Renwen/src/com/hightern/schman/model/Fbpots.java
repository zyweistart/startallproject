/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.model;

//帖子

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "fbpots")
@Table(name = "SCH_Fbpots")
public class Fbpots extends BaseModel {
    public Fbpots() {
    }

    public Fbpots(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //帖子标题
    @Column
    private String fpTitle;
    
    //作者
    @Column
    private String author;
    
    //头像
    @Column
    private String avatar;
    
    //用户ID
    @Column
    private Long usersId;
    
    //用户姓名
    @Column
    private String usersName;
    
    //帖子内容
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String fpContent;
    
    //发布日期
    @Column
    private String startDay;
    
    //使用状态
    @Column
    private Integer fbStatus;
   
    //浏览量
    @Column
    private String pviews;
    
    //置顶状态
    @Column
    private Integer topStatus;
    

    //版块ID
    @Column
    private Long forumId;
    
    //版块名称
    @Column
    private String forumName;
    

    
    
    
    
    public Long getForumId() {
		return forumId;
	}
    
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPviews() {
		return pviews;
	}

	public void setPviews(String pviews) {
		this.pviews = pviews;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public Integer getTopStatus() {
		return topStatus;
	}

	public void setTopStatus(Integer topStatus) {
		this.topStatus = topStatus;
	}

	public String getFpTitle() {
		return fpTitle;
	}

	public void setFpTitle(String fpTitle) {
		this.fpTitle = fpTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	public String getFpContent() {
		return fpContent;
	}

	public void setFpContent(String fpContent) {
		this.fpContent = fpContent;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public Integer getFbStatus() {
		return fbStatus;
	}

	public void setFbStatus(Integer fbStatus) {
		this.fbStatus = fbStatus;
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