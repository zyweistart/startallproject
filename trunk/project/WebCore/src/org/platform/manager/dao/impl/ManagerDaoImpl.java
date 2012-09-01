package org.platform.manager.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.manager.dao.ManagerDao;
import org.platform.manager.entity.Manager;

@Repository("managerDao")
public final class ManagerDaoImpl extends RootDaoImpl<Manager,String>implements ManagerDao {

	public ManagerDaoImpl() {
		super(Manager.class);
	}

}
