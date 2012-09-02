/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.model;
//用户留言  (反馈)
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.model.BaseModel;

@Entity(name = "fdbmag")
@Table(name = "SCH_Fdbmag")
public class Fdbmag extends BaseModel {
    public Fdbmag() {
    }

    public Fdbmag(HttpServletRequest request, String tableId) {
        super(request, tableId);
    }

    private static final long serialVersionUID = 1L;

    //TODO:在此添加相关字段属性

    //反馈问题
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String fproblem;
    
    //反馈者
    @Column
    private String fauthor;
    
    //反馈日期
    @Column
    private String srartDay;
    
    //回复状态(1:已回复, 2:未回复)
    @Column
    private Integer  replyStatus;
    
    //回复内容
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String answerMag;
    
    //回复日期
    @Column
    private String endDay;
    
    
    
    
    public String getFproblem() {
		return fproblem;
	}

	public void setFproblem(String fproblem) {
		this.fproblem = fproblem;
	}

	public String getFauthor() {
		return fauthor;
	}

	public void setFauthor(String fauthor) {
		this.fauthor = fauthor;
	}

	public String getSrartDay() {
		return srartDay;
	}

	public void setSrartDay(String srartDay) {
		this.srartDay = srartDay;
	}

	public Integer getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Integer replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getAnswerMag() {
		return answerMag;
	}

	public void setAnswerMag(String answerMag) {
		this.answerMag = answerMag;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
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