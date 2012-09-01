package org.platform.manager.service.impl;

import org.framework.service.impl.RootServiceImpl;
import org.platform.manager.dao.ManagerDao;
import org.platform.manager.entity.Manager;
import org.platform.manager.service.ManagerService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("managerService")
public final class ManagerServiceImpl extends RootServiceImpl<Manager,String> 
implements ManagerService {

	private ManagerDao managerDao;
	
	public ManagerServiceImpl(@Qualifier("managerDao")ManagerDao managerDao) {
		super(managerDao);
		this.managerDao=managerDao;
	}

	private final static String SQL_isPresenceByName="SELECT COUNT(1) FROM MAN_MANAGER WHERE USERNAME=?";
	@Override
	public Boolean isPresenceByName(String name) {
		return managerDao.polymerizationQuery(SQL_isPresenceByName,name)!=0;
	}

	private final static String SQL_loadManagerByUserName="SELECT * FROM MAN_MANAGER WHERE USERNAME=?";
	@Override
	public Manager loadManagerByUserName(String username) {
		return createNativeSQLOnlySingle(SQL_loadManagerByUserName, username);
	}

}