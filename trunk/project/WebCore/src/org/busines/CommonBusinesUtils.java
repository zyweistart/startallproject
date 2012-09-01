package org.busines;

import java.sql.SQLException;

import org.framework.exception.AppRuntimeException;
import org.framework.utils.LogUtils;
import org.message.IMsg;
import org.zyweistartframework.persistence.ds.Transaction;

public class CommonBusinesUtils {
	/**
	 * 事务异常处理
	 */
	public static Integer exception(Transaction transaction,Exception e,String traceInfo){
		Integer result=IMsg._999;
		//如果由AppRuntimeException引发的异常则不记录日志,因为由AppRuntimeException引发的异常都以记录
		if(e.getClass().equals(AppRuntimeException.class)){
			result=Integer.parseInt(e.getMessage());
		}else{
			LogUtils.logError(traceInfo + e.getMessage());
		}
		if(transaction!=null){
			try {
				transaction.rollbackTrans();
			} catch (SQLException ex) {
				LogUtils.logError(traceInfo + ex.getMessage());
				result=IMsg._999;
			}
		}
		return result;
	}
	
}