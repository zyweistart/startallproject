package org.platform.manager.entity;

import org.platform.system.entity.OperatorLog;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.Table;

@Entity("managerOperatorLog")
@Table("MAN_MANAGEROPERATORLOG")
public class ManagerOperatorLog extends OperatorLog {

	private static final long serialVersionUID = 1L;
	
	public ManagerOperatorLog(){}
	/**
	 * 管理员登陆日志
	 */
	@ManyToOne
	private ManagerLoginLog managerLoginLog;

	public ManagerLoginLog getManagerLoginLog() {
		return managerLoginLog;
	}

	public void setManagerLoginLog(ManagerLoginLog managerLoginLog) {
		this.managerLoginLog = managerLoginLog;
	}
	
}
