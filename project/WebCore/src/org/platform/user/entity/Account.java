package org.platform.user.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.OneToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
/**
 * 账户信息
 * @author Start
 */
@Entity("account")
@Table("USE_ACCOUNT")
public class Account extends Root {

	private static final long serialVersionUID = 1L;
	
	public Account(){}
	/**
	 * 用户类型
	 */
	@Column(nullable=false)
	private Integer userType;
	/**
	 * 注册来源
	 */
	@Column(nullable=false)
	private Integer registerSource;
	/**
	 * 注册IP地址
	 */
	@Column(length=63,nullable=false)
	private String registerIP;
	/**
	 * 注册时间
	 */
	@Temporal(nullable=false)
	private String registerTime;
	/**
	 * 用户验证连续错误次数
	 */
	@Column(nullable=false)
	private Integer loginErrorCount;
	/**
	 * 登录次数
	 */
	@Column(nullable=false)
	private Integer loginCount;
	/**
	 * 是否需要修改密码(0:需要;1:不需要)
	 */
	@Column(nullable=false)
	private Boolean pwdVerifyflag;
	/**
	 * 用户状态(1:正常;2:暂停;3:注销)
	 */
	@Column(nullable=false)
	private Integer status;
	
	@OneToOne
	private User user;

	public Integer getRegisterSource() {
		return registerSource;
	}
	public void setRegisterSource(Integer registerSource) {
		this.registerSource = registerSource;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getRegisterIP() {
		return registerIP;
	}
	public void setRegisterIP(String registerIP) {
		this.registerIP = registerIP;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public Integer getLoginErrorCount() {
		return loginErrorCount;
	}
	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Boolean getPwdVerifyflag() {
		return pwdVerifyflag;
	}
	public void setPwdVerifyflag(Boolean pwdVerifyflag) {
		this.pwdVerifyflag = pwdVerifyflag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}