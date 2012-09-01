package org.platform.manager.service.impl;

import org.framework.config.Business;
import org.framework.service.impl.RootServiceImpl;
import org.platform.manager.dao.ManagerLoginLogDao;
import org.platform.manager.entity.ManagerLoginLog;
import org.platform.manager.service.ManagerLoginLogService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("managerLoginLogService")
public final class ManagerLoginLogServiceImpl extends RootServiceImpl<ManagerLoginLog,String> 
implements ManagerLoginLogService {

	private ManagerLoginLogDao managerLoginLogDao;
	
	public ManagerLoginLogServiceImpl(@Qualifier("managerLoginLogDao")ManagerLoginLogDao managerLoginLogDao) {
		super(managerLoginLogDao);
		this.managerLoginLogDao=managerLoginLogDao;
	}
	private final static String SQL_ManagerLoginLog_save="INSERT INTO MAN_MANAGERLOGINLOG(ID,ACCESSID,ACCESSKEY," +
			"ONLINESTATUS,LOGINIP,LOGINTIME,INVALIDTIME,MANAGER) " +
			"VALUES(?,?,?,?,?,SYSDATE,SYSDATE+?/24,?)";
	@Override
	public void save(ManagerLoginLog managerLoginLog) {
		managerLoginLogDao.executeUpdate(SQL_ManagerLoginLog_save,
				managerLoginLog.getId(),
				managerLoginLog.getAccessId(),
				managerLoginLog.getAccessKey(),
				Constants.TRUE,
				managerLoginLog.getLoginIP(),
				Business.MANAGERLOGINLOG_ACTIVATION_HOUR,
				managerLoginLog.getManager().getId());
	}

	private final static String SQL_loadByAccessID="SELECT * FROM MAN_MANAGERLOGINLOG WHERE ACCESSID=?";
	@Override
	public ManagerLoginLog loadByAccessID(String accessID){
		return createNativeSQLOnlySingle(SQL_loadByAccessID,accessID);
	}
	
	private final static String SQL_activationManagerLoginLog="UPDATE MAN_MANAGERLOGINLOG SET INVALIDTIME=SYSDATE+?/24 " +
			"WHERE ACCESSID=?  AND ONLINESTATUS=? AND INVALIDTIME>SYSDATE";
	@Override
	public void activationManagerLoginLog(String accessID) {
		managerLoginLogDao.executeUpdate(SQL_activationManagerLoginLog,
				Business.MANAGERLOGINLOG_ACTIVATION_HOUR,
				accessID,
				Constants.TRUE);
	}
	
	private final static String SQL_logout="UPDATE MAN_MANAGERLOGINLOG SET ONLINESTATUS=?,QUITTIME=SYSDATE,QUITREMARK=? " +
			"WHERE ACCESSID=? AND ONLINESTATUS=? AND INVALIDTIME>SYSDATE";
	@Override
	public void logout(ManagerLoginLog managerLoginLog) {
		managerLoginLogDao.executeUpdate(SQL_logout,
				Constants.FALSE,
				managerLoginLog.getQuitRemark(),
				managerLoginLog.getAccessId(),
				Constants.TRUE);
	}
}
