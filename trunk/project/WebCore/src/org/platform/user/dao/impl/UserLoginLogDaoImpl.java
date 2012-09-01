package org.platform.user.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.user.dao.UserLoginLogDao;
import org.platform.user.entity.UserLoginLog;

@Repository("userLoginLogDao")
public final class UserLoginLogDaoImpl extends RootDaoImpl<UserLoginLog,String>implements UserLoginLogDao {

	public UserLoginLogDaoImpl() {
		super(UserLoginLog.class);
	}

}
