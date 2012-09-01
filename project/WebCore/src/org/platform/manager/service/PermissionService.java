package org.platform.manager.service;

import java.util.List;

import org.framework.service.RootService;
import org.platform.manager.entity.Permission;

public interface PermissionService extends RootService<Permission,String> {
	/**
	 * 当前权限编号是否已经存在
	 */
	Boolean isPresenceByAction(String action);
	
	Permission loadPermissionByAction(String action);

	List<Permission> loadPermissionsByActions(String...actions);
}
