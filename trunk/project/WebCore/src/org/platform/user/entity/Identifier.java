package org.platform.user.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.JoinColumn;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
/**
 * 帐户识别码表
 * @author Start
 */
@Entity("identifier")
@Table("USE_IDENTIFIER")
public class Identifier extends Root {

	private static final long serialVersionUID = 1L;
	
	public Identifier(){}
	/**
	 * 类别(邮箱验证(帐户激活);密码找回;手机验证)
	 */
	@Column(nullable=false)
	private Integer type;
	/**
	 * 密钥DES加密形式
	 */
	@Column(length=128,nullable=false,unique=true)
	private String passkey;
	/**
	 * 状态(未使用;已使用;已失效)
	 */
	@Column(nullable=false)
	private Integer useStatus;
	/**
	 * 生成IP地址
	 */
	@Column(length=63,nullable=false)
	private String generateIP;
	/**
	 * 生成时间
	 */
	@Temporal(nullable=false)
	private String generateTime;
	/**
	 * 处理IP地址
	 */
	@Column(length=63)
	private String handleIP;
	/**
	 * 处理时间
	 */
	@Temporal
	private String handleTime;
	/**
	 * 失效时间
	 */
	@Temporal(nullable=false)
	private String invalidTime;
	/**
	 * 用户主键
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn("userId")
	private User user;

	public String getPasskey() {
		return passkey;
	}
	
	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}
	
	public Integer getUseStatus() {
		return useStatus;
	}
	
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	
	public String getGenerateIP() {
		return generateIP;
	}
	
	public void setGenerateIP(String generateIP) {
		this.generateIP = generateIP;
	}
	
	public String getGenerateTime() {
		return generateTime;
	}
	
	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}
	
	public String getHandleIP() {
		return handleIP;
	}
	
	public void setHandleIP(String handleIP) {
		this.handleIP = handleIP;
	}
	
	public String getHandleTime() {
		return handleTime;
	}
	
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}
	
	public String getInvalidTime() {
		return invalidTime;
	}
	
	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}