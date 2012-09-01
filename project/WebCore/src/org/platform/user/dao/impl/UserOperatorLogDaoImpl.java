package org.platform.user.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.user.dao.UserOperatorLogDao;
import org.platform.user.entity.UserOperatorLog;

@Repository("userOperatorLogDao")
public final class UserOperatorLogDaoImpl extends RootDaoImpl<UserOperatorLog,String>implements UserOperatorLogDao {

	public UserOperatorLogDaoImpl() {
		super(UserOperatorLog.class);
	}

}
