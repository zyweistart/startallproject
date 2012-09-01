package org.platform.system.service.impl;

import org.framework.service.impl.RootServiceImpl;
import org.framework.utils.StringUtils;
import org.platform.system.dao.OperatorLogDao;
import org.platform.system.entity.OperatorLog;
import org.platform.system.service.OperatorLogService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("operatorLogService")
public final class OperatorLogServiceImpl extends RootServiceImpl<OperatorLog,String> 
implements OperatorLogService {

	private OperatorLogDao operatorLogDao;
	
	public OperatorLogServiceImpl(@Qualifier("operatorLogDao")OperatorLogDao operatorLogDao) {
		super(operatorLogDao);
		this.operatorLogDao=operatorLogDao;
	}

	private final static String SQL_operatorLog_save="INSERT INTO SYS_OPERATORLOG(" +
			"ID,BLWKINDEX,ACTION,REQUESTIP,REQUESTTIME,SERVLETPATH,SERVERTIME,DATABASETIME,CONTENT) " +
			"VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),SYSDATE,?)";
	@Override
	public void save(OperatorLog operatorLog) {
		operatorLogDao.executeUpdate(SQL_operatorLog_save,
				StringUtils.random(),
				operatorLog.getBlwkIndex(),
				operatorLog.getAction(),
				operatorLog.getRequestIP(),
				operatorLog.getRequestTime(),
				operatorLog.getServletPath(),
				operatorLog.getServerTime(),
				operatorLog.getContent());
	}
	
}
