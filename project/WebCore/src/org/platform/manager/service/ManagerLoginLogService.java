package org.platform.manager.service;

import org.framework.service.RootService;
import org.platform.manager.entity.ManagerLoginLog;

public interface ManagerLoginLogService extends RootService<ManagerLoginLog,String> {
	/**
	 * 加载当前登陆信息
	 */
	ManagerLoginLog loadByAccessID(String accessID);
	/**
	 * 激活当前登陆信息
	 */
	void activationManagerLoginLog(String accessID) ;
	/**
	 * 管理员退出登陆
	 */
	void logout(ManagerLoginLog managerLoginLog);
}
