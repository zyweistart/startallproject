package org.platform.manager.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.manager.dao.RoleDao;
import org.platform.manager.entity.Role;

@Repository("roleDao")
public final class RoleDaoImpl extends RootDaoImpl<Role,String>implements RoleDao {

	public RoleDaoImpl() {
		super(Role.class);
	}

}
