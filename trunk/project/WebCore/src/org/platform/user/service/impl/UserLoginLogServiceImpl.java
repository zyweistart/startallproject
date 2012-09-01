package org.platform.user.service.impl;

import org.framework.config.Business;
import org.framework.service.impl.RootServiceImpl;
import org.platform.user.dao.UserLoginLogDao;
import org.platform.user.entity.Account;
import org.platform.user.entity.UserLoginLog;
import org.platform.user.service.UserLoginLogService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("userLoginLogService")
public final class UserLoginLogServiceImpl extends RootServiceImpl<UserLoginLog,String> 
implements UserLoginLogService {

	private UserLoginLogDao userLoginLogDao;
	
	public UserLoginLogServiceImpl(@Qualifier("userLoginLogDao")UserLoginLogDao userLoginLogDao) {
		super(userLoginLogDao);
		this.userLoginLogDao=userLoginLogDao;
	}
	
	private final static String SQL_userLoginLog_save="INSERT INTO USE_USERLOGINLOG(ID,ACCESSID,ACCESSKEY," +
			"ONLINESTATUS,SOURCE,LOGINMODE,LOGINIP,LOGINTIME,INVALIDTIME,USERID) " +
			"VALUES(?,?,?,?,?,?,?,SYSDATE,SYSDATE+?/24,?)";
	@Override
	public void save(UserLoginLog userLoginLog) {
		userLoginLogDao.executeUpdate(SQL_userLoginLog_save,
				userLoginLog.getId(),
				userLoginLog.getAccessId(),
				userLoginLog.getAccessKey(),
				Constants.TRUE,
				userLoginLog.getSource(),
				userLoginLog.getLoginMode(),
				userLoginLog.getLoginIP(),
				Business.USERLOGINLOG_ACTIVATION_HOUR,
				userLoginLog.getUser().getId());
	}

	private final static String SQL_loadByAccessID="SELECT * FROM USE_USERLOGINLOG WHERE ACCESSID=?";
	@Override
	public UserLoginLog loadByAccessID(String accessID){
		return createNativeSQLOnlySingle(SQL_loadByAccessID,accessID);
	}
	
	private final static String SQL_activationUserLoginLog="UPDATE USE_USERLOGINLOG SET INVALIDTIME=SYSDATE+?/24 " +
			"WHERE ACCESSID=?  AND ONLINESTATUS=? AND INVALIDTIME>SYSDATE";
	@Override
	public void activationUserLoginLog(String accessID) {
		userLoginLogDao.executeUpdate(SQL_activationUserLoginLog,
				Business.USERLOGINLOG_ACTIVATION_HOUR,
				accessID,
				Constants.TRUE);
	}
	
	private final static String SQL_login_success="UPDATE USE_ACCOUNT SET LOGINCOUNT=LOGINCOUNT+1," +
			"LOGINERRORCOUNT=0 WHERE ID=?";
	private final static String SQL_login_fail="UPDATE USE_ACCOUNT SET " +
			"LOGINERRORCOUNT=LOGINERRORCOUNT+1 WHERE ID=?";
	@Override
	public void login(Account account,Boolean loginStatus) {
		userLoginLogDao.executeUpdate(loginStatus?SQL_login_success:SQL_login_fail,account.getId());
	}
	
	private final static String SQL_logout="UPDATE USE_USERLOGINLOG SET ONLINESTATUS=?,QUITTIME=SYSDATE,QUITREMARK=? " +
			"WHERE ACCESSID=? AND ONLINESTATUS=? AND INVALIDTIME>SYSDATE";
	@Override
	public void logout(UserLoginLog userLoginLog) {
		userLoginLogDao.executeUpdate(SQL_logout,
				Constants.FALSE,
				userLoginLog.getQuitRemark(),
				userLoginLog.getAccessId(),
				Constants.TRUE);
	}
	
}