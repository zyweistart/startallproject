package org.platform.manager.service;

import org.framework.service.RootService;
import org.platform.manager.entity.Manager;

public interface ManagerService extends RootService<Manager,String> {
	/**
	 * 当前管理员是否已经存在
	 */
	Boolean isPresenceByName(String name);
	
	Manager loadManagerByUserName(String username);
	
}
