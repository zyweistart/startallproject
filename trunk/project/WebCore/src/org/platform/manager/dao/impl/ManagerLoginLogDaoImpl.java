package org.platform.manager.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.manager.dao.ManagerLoginLogDao;
import org.platform.manager.entity.ManagerLoginLog;

@Repository("managerLoginLogDao")
public final class ManagerLoginLogDaoImpl extends RootDaoImpl<ManagerLoginLog,String>implements ManagerLoginLogDao {

	public ManagerLoginLogDaoImpl() {
		super(ManagerLoginLog.class);
	}

}
