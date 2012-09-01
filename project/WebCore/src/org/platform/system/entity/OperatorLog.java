package org.platform.system.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;

@Entity("operatorLog")
@Table("SYS_OPERATORLOG")
public class OperatorLog extends Root {

	private static final long serialVersionUID = 1L;
	
	public OperatorLog(){}
	/**
	 * 负载索引
	 */
	@Column(nullable=false)
	private Integer blwkIndex;
	/**
	 * 访问的路径
	 */
	@Column(length=200,nullable=false)
	private String servletPath;
	/**
	 * 动作说明
	 */
	@Column(length=100,nullable=false)
	private String action;
	/**
	 * 日志内容
	 */
	@Lob
	private String content;
	/**
	 * 请求IP地址
	 */
	@Column(length=63,nullable=false)
	private String requestIP;
	/**
	 * 请求时间
	 */
	@Temporal(nullable=false)
	private String requestTime;
	/**
	 * 服务器时间
	 */
	@Temporal(nullable=false)
	private String serverTime;
	/**
	 * 数据库时间
	 */
	@Temporal(nullable=false)
	private String databaseTime;

	public Integer getBlwkIndex() {
		return blwkIndex;
	}
	
	public void setBlwkIndex(Integer blwkIndex) {
		this.blwkIndex = blwkIndex;
	}
	
	public String getServletPath() {
		return servletPath;
	}
	
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRequestIP() {
		return requestIP;
	}
	
	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	
	public String getRequestTime() {
		return requestTime;
	}
	
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	
	public String getServerTime() {
		return serverTime;
	}
	
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	
	public String getDatabaseTime() {
		return databaseTime;
	}
	
	public void setDatabaseTime(String databaseTime) {
		this.databaseTime = databaseTime;
	}
	
}