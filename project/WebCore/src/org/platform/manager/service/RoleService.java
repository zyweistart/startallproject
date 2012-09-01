package org.platform.manager.service;

import java.util.List;

import org.framework.service.RootService;
import org.platform.manager.entity.Role;

public interface RoleService extends RootService<Role,String> {
	/**
	 * 当前角色是否已经存在
	 */
	Boolean isPresenceByName(String name);
	
	Role loadRoleByName(String name);
	
	List<Role> loadRolesByNames(String...names);
	
}