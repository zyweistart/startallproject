package org.platform.manager.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.manager.dao.PermissionDao;
import org.platform.manager.entity.Permission;

@Repository("permissionDao")
public final class PermissionDaoImpl extends RootDaoImpl<Permission,String>implements PermissionDao {

	public PermissionDaoImpl() {
		super(Permission.class);
	}

}
