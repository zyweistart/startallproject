package org.platform.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.framework.service.impl.RootServiceImpl;
import org.platform.manager.dao.RoleDao;
import org.platform.manager.entity.Role;
import org.platform.manager.service.RoleService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("roleService")
public final class RoleServiceImpl extends RootServiceImpl<Role,String> 
implements RoleService {

	private RoleDao roleDao;
	
	public RoleServiceImpl(@Qualifier("roleDao")RoleDao roleDao) {
		super(roleDao);
		this.roleDao=roleDao;
	}
	
	private final static String SQL_isPresenceByName="SELECT COUNT(1) FROM MAN_ROLE WHERE NAME=?";
	@Override
	public Boolean isPresenceByName(String name) {
		return roleDao.polymerizationQuery(SQL_isPresenceByName,name)!=0;
	}

	private final static String SQL_loadRoleByName="SELECT * FROM MAN_ROLE WHERE NAME=?";
	@Override
	public Role loadRoleByName(String name) {
		return createNativeSQLOnlySingle(SQL_loadRoleByName, name);
	}
	
	@Override
	public List<Role> loadRolesByNames(String...names) {
		List<Role> roles=new ArrayList<Role>();
		for(String name:names){
			Role role=loadRoleByName(name);
			if(role!=null){
				roles.add(role);
				role=null;
			}
		}
		return roles;
	}

}