package org.framework.entity;

import java.util.List;

import org.framework.request.RequestUtil;
import org.framework.utils.StringUtils;

public class PageQuery {
	
	private RequestUtil requestUtils;
	
	private List<Object> entitys;
	
	public PageQuery(RequestUtil requestUtils) {
		this.requestUtils = requestUtils;
	}
	
	public PageQuery(RequestUtil requestUtils,List<Object> entitys) {
		this.requestUtils = requestUtils;
		this.entitys=entitys;
	}

	private Integer totalCount;
	
	private Integer pageSize;
	
	private Integer totalPage;
	
	private Integer currentPage;
	/**
	 * 总记录数
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 每页的大小
	 */
	public Integer getPageSize() {
		if(pageSize==null){
			pageSize=StringUtils.nullToIntZero(requestUtils.getHeaderValue("pagesize"));
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 总页数
	 */
	public Integer getTotalPage() {
		return totalPage;
	}
	/**
	 * 当前的页数
	 */
	public Integer getCurrentPage() {
		if(currentPage==null){
			currentPage=StringUtils.nullToIntZero(requestUtils.getHeaderValue("currentpage"));
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<Object> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<Object> entitys) {
		this.entitys = entitys;
	}
	
}