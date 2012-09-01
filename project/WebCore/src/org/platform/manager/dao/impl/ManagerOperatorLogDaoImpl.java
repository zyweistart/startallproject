package org.platform.manager.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.manager.dao.ManagerOperatorLogDao;
import org.platform.manager.entity.ManagerOperatorLog;

@Repository("managerOperatorLogDao")
public final class ManagerOperatorLogDaoImpl extends RootDaoImpl<ManagerOperatorLog,String>implements ManagerOperatorLogDao {

	public ManagerOperatorLogDaoImpl() {
		super(ManagerOperatorLog.class);
	}

}
