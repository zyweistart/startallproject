package org.platform.system.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.system.dao.OperatorLogDao;
import org.platform.system.entity.OperatorLog;

@Repository("operatorLogDao")
public final class OperatorLogDaoImpl extends RootDaoImpl<OperatorLog,String>implements OperatorLogDao {

	public OperatorLogDaoImpl() {
		super(OperatorLog.class);
	}

}
