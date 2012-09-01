package org.platform.user.service.impl;

import org.framework.service.impl.RootServiceImpl;
import org.platform.user.dao.AccountDao;
import org.platform.user.entity.Account;
import org.platform.user.service.AccountService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("accountService")
public final class AccountServiceImpl extends RootServiceImpl<Account,String> 
implements AccountService {

	@SuppressWarnings("unused")
	private AccountDao accountDao;
	
	public AccountServiceImpl(@Qualifier("accountDao")AccountDao accountDao) {
		super(accountDao);
		this.accountDao=accountDao;
	}

}