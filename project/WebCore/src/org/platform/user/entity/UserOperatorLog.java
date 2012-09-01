package org.platform.user.entity;

import org.platform.system.entity.OperatorLog;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 用户操作日志
 * @author Start
 */
@Entity("userOperatorLog")
@Table("USE_USEROPERATORLOG")
public class UserOperatorLog extends OperatorLog {

	private static final long serialVersionUID = 1L;
	
	public UserOperatorLog(){}
	/**
	 * 用户登陆日志
	 */
	@ManyToOne
	private UserLoginLog userLoginLog;

	public UserLoginLog getUserLoginLog() {
		return userLoginLog;
	}

	public void setUserLoginLog(UserLoginLog userLoginLog) {
		this.userLoginLog = userLoginLog;
	}

}