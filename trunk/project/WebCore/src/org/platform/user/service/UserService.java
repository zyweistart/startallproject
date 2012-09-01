package org.platform.user.service;

import org.framework.service.RootService;
import org.platform.user.entity.Identifier;
import org.platform.user.entity.User;

public interface UserService extends RootService<User,String> {
	/**
	 * 检测电子邮件是否已经存在
	 */
	boolean isPresenceByEmail(String email);
	/**
	 * 根据电子邮件获取用户信息
	 */
	User loadUserByEmail(String email);
	/**
	 * 密码修改
	 */
	void modifyPassword(User user);
	/**
	 * 重置密码
	 */
	void resetPassword(User user,Identifier identifer);
}
