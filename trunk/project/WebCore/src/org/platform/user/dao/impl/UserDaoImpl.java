package org.platform.user.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.user.dao.UserDao;
import org.platform.user.entity.User;

@Repository("userDao")
public final class UserDaoImpl extends RootDaoImpl<User,String>implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

}
