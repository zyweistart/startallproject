package org.platform.user.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.user.dao.AccountDao;
import org.platform.user.entity.Account;

@Repository("accountDao")
public final class AccountDaoImpl extends RootDaoImpl<Account,String>implements AccountDao {

	public AccountDaoImpl() {
		super(Account.class);
	}

}
