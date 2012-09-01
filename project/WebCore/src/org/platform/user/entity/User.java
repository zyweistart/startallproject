package org.platform.user.entity;

import java.util.List;

import org.framework.entity.Root;
import org.platform.file.entity.UploadApply;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.OneToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.Transient;
import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
/**
 * 用户信息
 * @author Start
 */
@Entity("user")
@Table("USE_USER")
public class User extends Root {

	private static final long serialVersionUID = 1L;
	
	public User(){}
	/**
	 * 电子邮箱
	 */
	@Column(length=64,nullable=false,unique=true)
	private String email;
	/**
	 * 邮箱验证标志(0:未验证;1:已验证)
	 */
	@Column(nullable=false)
	private Boolean emailVerifyflag;
	/**
	 * 激活状态(0:未激活;1:已激活)
	 */
	@Column(nullable=false)
	private Boolean activeStatus;
	/**
	 * 密码(32位MD5值DES加密)
	 */
	@Column(length=128,nullable=false)
	private String password;
	/**
	 * 实名登记标志(false:否;true:是)
	 */
	@Column(nullable=false)
	private Boolean realnameflag;
	/**
	 * 实名登记时间
	 */
	@Temporal
	private String realnameTime;
	/**
	 * 实名认证标志(false:否;true:是)
	 */
	@Column(nullable=false)
	private Boolean certificationflag;
	/**
	 * 实名认证时间
	 */
	@Temporal
	private String certificationTime;
	/**
	 * 账户,立即加载
	 */
	@OneToOne(cascade={CascadeType.All},fetch=FetchType.EAGER,mapping=true)
	private Account account;
	
	@OneToMany
	private List<Identifier> identifiers;
	@OneToMany
	private List<UserLoginLog> userLoginLogs;
	@OneToMany
	private List<UploadApply> uploadApplys;
	/**
	 * 用户类型
	 */
	@Transient
	private Integer userType;
	/**
	 * 来源
	 */
	@Transient
	private Integer source;
	/**
	 * IP地址
	 */
	@Transient
	private String IP;
	/**
	 * 登录方式(直接登录;单点登录;激活登录)
	 */
	@Transient
	private Integer loginMode;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getEmailVerifyflag() {
		return emailVerifyflag;
	}
	public void setEmailVerifyflag(Boolean emailVerifyflag) {
		this.emailVerifyflag = emailVerifyflag;
	}
	public Boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getRealnameflag() {
		return realnameflag;
	}
	public void setRealnameflag(Boolean realnameflag) {
		this.realnameflag = realnameflag;
	}
	public String getRealnameTime() {
		return realnameTime;
	}
	public void setRealnameTime(String realnameTime) {
		this.realnameTime = realnameTime;
	}
	public Boolean getCertificationflag() {
		return certificationflag;
	}
	public void setCertificationflag(Boolean certificationflag) {
		this.certificationflag = certificationflag;
	}
	public String getCertificationTime() {
		return certificationTime;
	}
	public void setCertificationTime(String certificationTime) {
		this.certificationTime = certificationTime;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	public List<UserLoginLog> getUserLoginLogs() {
		return userLoginLogs;
	}
	public void setUserLoginLogs(List<UserLoginLog> userLoginLogs) {
		this.userLoginLogs = userLoginLogs;
	}
	public List<UploadApply> getUploadApplys() {
		return uploadApplys;
	}
	public void setUploadApplys(List<UploadApply> uploadApplys) {
		this.uploadApplys = uploadApplys;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public Integer getLoginMode() {
		return loginMode;
	}
	public void setLoginMode(Integer loginMode) {
		this.loginMode = loginMode;
	}
	
}