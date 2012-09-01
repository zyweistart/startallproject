package org.platform.file.entity;

import org.framework.entity.Root;
import org.platform.user.entity.User;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.JoinColumn;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
/**
 * 文件上传申请
 * @author Start
 */
@Entity("uploadApply")
@Table("FIL_UPLOADAPPLY")
public class UploadApply extends Root {

	private static final long serialVersionUID = 1L;
	
	public UploadApply(){}
	/**
	 * 申请大小
	 */
	@Column(nullable=false)
	private Integer applySize;
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

	public Integer getApplySize() {
		return applySize;
	}

	public void setApplySize(Integer applySize) {
		this.applySize = applySize;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}