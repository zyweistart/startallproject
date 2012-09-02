/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.hightern.kernel.model;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.util.WebUtils;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.context.HttpServletRequestContext;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.filter.ExportFilterUtils;
import com.hightern.ecside.table.limit.Limit;
import com.hightern.ecside.table.limit.LimitFactory;
import com.hightern.ecside.table.limit.TableLimit;
import com.hightern.ecside.table.limit.TableLimitFactory;
import com.opensymphony.xwork2.ActionContext;

/**
 * 分页用到的基类
 * 
 * @author Stone
 */
public class BaseQuery implements Serializable {
	
	private static final long serialVersionUID = -4836646895024994973L;
	private int pageSize = 20;// 页面大小
	private int pageNo = 1;// 页号
	private transient int totalRows = 0;// 总记录数
	private transient int pageCount = 0;// 总页数
	private boolean all = false;
	@SuppressWarnings({  "rawtypes" })
	private Map orderMap = null;
	private Limit limit;
	private HttpServletRequest request;
	
	public BaseQuery() {}
	
	public BaseQuery(HttpServletRequest request, String tableId) {
		this.request = request;
		if (tableId == null) {
			tableId = "ec";
		}
		if (request != null) {
			final Context context = new HttpServletRequestContext(request);
			final LimitFactory limitFactory = new TableLimitFactory(context, tableId);
			limit = new TableLimit(limitFactory);
			this.pageNo = limit.getPage();
			final String rcdStr = context.getParameter(tableId + "_" + TableConstants.CURRENT_ROWS_DISPLAYED);
			if (StringUtils.isNotBlank(rcdStr)) {
				this.pageSize = Integer.parseInt(rcdStr);
			}
			if (ExportFilterUtils.isExported(context)) {
				all = true;
			}
			orderMap = WebUtils.getParametersStartingWith(request, tableId + "_" + TableConstants.SORT);
		}
	}
	
	public BaseQuery(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getEndSize() {
		return pageSize * (pageNo - 1);
	}
	
	@SuppressWarnings({  "rawtypes" })
	public Map getOrderMap() {
		return orderMap;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getTotalRows() {
		return totalRows;
	}
	
	public boolean isAll() {
		return all;
	}
	
	public void setAll(boolean all) {
		this.all = all;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		if (limit != null) {
			limit.setRowAttributes(totalRows, pageSize);
		}
		if(request == null ){
			ActionContext ctx = ActionContext.getContext(); 
			request =  (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
		}
		request.setAttribute("totalRows", new Integer(totalRows));
	}
}
