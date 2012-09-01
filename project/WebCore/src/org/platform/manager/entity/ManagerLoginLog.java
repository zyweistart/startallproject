package org.platform.manager.entity;

import java.util.List;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.FetchType;

@Entity("managerLoginLog")
@Table("MAN_MANAGERLOGINLOG")
public class ManagerLoginLog extends Root {

	private static final long serialVersionUID = 1L;
	
	public ManagerLoginLog(){}
	/**
	 * 通行证编号
	 */
	@Column(length=32,nullable=false,unique=true)
	private String accessId;
	/**
	 * 通行证密钥
	 */
	@Column(length=128,nullable=false,unique=true)
	private String accessKey;
	/**
	 * 在线状态(false:离线;true:在线;)
	 */
	@Column(nullable=false)
	private Boolean onlineStatus;
	/**
	 * 失效时间--默认登陆后一小时失效
	 */
	@Temporal(nullable=false)
	private String invalidTime;
	/**
	 * 登陆IP地址
	 */
	@Column(length=63,nullable=false)
	private String loginIP;
	/**
	 * 登录时间
	 */
	@Temporal(nullable=false)
	private String loginTime;
	/**
	 * 登录备注
	 */
	@Column(length=200)
	private String loginRemark;
	/**
	 * 退出时间
	 */
	@Temporal
	private String quitTime;
	/**
	 * 退出备注
	 */
	@Column(length=200)
	private String quitRemark;
	/**
	 * 管理员
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Manager manager;
	
	@OneToMany
	private List<ManagerOperatorLog> managerOperatorLogs;

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public Boolean getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginRemark() {
		return loginRemark;
	}

	public void setLoginRemark(String loginRemark) {
		this.loginRemark = loginRemark;
	}

	public String getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}

	public String getQuitRemark() {
		return quitRemark;
	}

	public void setQuitRemark(String quitRemark) {
		this.quitRemark = quitRemark;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<ManagerOperatorLog> getManagerOperatorLogs() {
		return managerOperatorLogs;
	}

	public void setManagerOperatorLogs(List<ManagerOperatorLog> managerOperatorLogs) {
		this.managerOperatorLogs = managerOperatorLogs;
	}

}