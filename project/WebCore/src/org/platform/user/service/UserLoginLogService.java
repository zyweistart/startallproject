package org.platform.user.service;

import org.framework.service.RootService;
import org.platform.user.entity.Account;
import org.platform.user.entity.UserLoginLog;

public interface UserLoginLogService extends RootService<UserLoginLog,String> {
	/**
	 * 加载当前登陆信息
	 */
	UserLoginLog loadByAccessID(String accessID);
	/**
	 * 激活当前登陆信息
	 */
	void activationUserLoginLog(String accessID) ;
	/**
	 * 用户登陆
	 */
	void login(Account account,Boolean loginStatus);
	/**
	 * 退出
	 */
	void logout(UserLoginLog loginLog);
}