package org.platform.user.service.impl;

import org.framework.service.impl.RootServiceImpl;
import org.framework.utils.StringUtils;
import org.platform.user.dao.UserOperatorLogDao;
import org.platform.user.entity.UserOperatorLog;
import org.platform.user.service.UserOperatorLogService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("userOperatorLogService")
public final class UserOperatorLogServiceImpl extends RootServiceImpl<UserOperatorLog,String> 
implements UserOperatorLogService {
	
	private UserOperatorLogDao userOperatorLogDao;
	
	public UserOperatorLogServiceImpl(@Qualifier("userOperatorLogDao")UserOperatorLogDao userOperatorLogDao) {
		super(userOperatorLogDao);
		this.userOperatorLogDao=userOperatorLogDao;
	}

	private final static String SQL_userOperatorLog_save="INSERT INTO USE_USEROPERATORLOG(" +
			"ID,BLWKINDEX,ACTION,REQUESTIP,REQUESTTIME,SERVLETPATH,SERVERTIME,DATABASETIME,CONTENT,USERLOGINLOG) " +
			"VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),SYSDATE,?,?)";
	@Override
	public void save(UserOperatorLog userOperatorLog) {
		userOperatorLogDao.executeUpdate(SQL_userOperatorLog_save,
				StringUtils.random(),
				userOperatorLog.getBlwkIndex(),
				userOperatorLog.getAction(),
				userOperatorLog.getRequestIP(),
				userOperatorLog.getRequestTime(),
				userOperatorLog.getServletPath(),
				userOperatorLog.getServerTime(),
				userOperatorLog.getContent(),
				userOperatorLog.getUserLoginLog().getId());
	}
	
}