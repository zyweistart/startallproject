package org.platform.manager.service.impl;

import org.framework.service.impl.RootServiceImpl;
import org.framework.utils.StringUtils;
import org.platform.manager.dao.ManagerOperatorLogDao;
import org.platform.manager.entity.ManagerOperatorLog;
import org.platform.manager.service.ManagerOperatorLogService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("managerOperatorLogService")
public final class ManagerOperatorLogServiceImpl extends RootServiceImpl<ManagerOperatorLog,String> 
implements ManagerOperatorLogService {

	private ManagerOperatorLogDao managerOperatorLogDao;
	
	public ManagerOperatorLogServiceImpl(@Qualifier("managerOperatorLogDao")ManagerOperatorLogDao managerOperatorLogDao) {
		super(managerOperatorLogDao);
		this.managerOperatorLogDao=managerOperatorLogDao;
	}
	
	private final static String SQL_managerOperatorLog_save="INSERT INTO MAN_MANAGEROPERATORLOG(" +
			"ID,BLWKINDEX,ACTION,REQUESTIP,REQUESTTIME,SERVLETPATH,SERVERTIME,DATABASETIME,CONTENT,MANAGERLOGINLOG) " +
			"VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),SYSDATE,?,?)";
	@Override
	public void save(ManagerOperatorLog managerOperatorLog) {
		managerOperatorLogDao.executeUpdate(SQL_managerOperatorLog_save,
				StringUtils.random(),
				managerOperatorLog.getBlwkIndex(),
				managerOperatorLog.getAction(),
				managerOperatorLog.getRequestIP(),
				managerOperatorLog.getRequestTime(),
				managerOperatorLog.getServletPath(),
				managerOperatorLog.getServerTime(),
				managerOperatorLog.getContent(),
				managerOperatorLog.getManagerLoginLog().getId());
	}

}
