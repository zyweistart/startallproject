package org.framework.action;

import java.sql.SQLException;

import org.framework.config.ConfigParameter;
import org.framework.exception.AppRuntimeException;
import org.framework.listener.CacheContext;
import org.framework.request.RequestUtil;
import org.framework.result.ResponseUtil;
import org.framework.result.XML;
import org.framework.utils.LogUtils;
import org.framework.utils.PropertiesUtils;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;
import org.message.IMsg;
import org.platform.system.entity.OperatorLog;
import org.platform.system.service.OperatorLogService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Resource;
import org.zyweistartframework.controller.ActionSupport;
import org.zyweistartframework.controller.IActionResult;
import org.zyweistartframework.controller.IInterceptor.ControllerInvocation.InvokeAction;
import org.zyweistartframework.utils.StackTraceInfo;

public class RootAction extends ActionSupport {
	
	protected IActionResult result;
	protected OperatorLog operatorLog;
	
	private RequestUtil requestUtil;
	private ResponseUtil responseUtil;
	
	@Resource
	protected OperatorLogService operatorLogService;
	
	protected RequestUtil getRequestUtil() {
		if(requestUtil==null){
			requestUtil=new RequestUtil(request);
		}
		return requestUtil;
	}
	
	protected ResponseUtil getResponseUtil() {
		if(responseUtil==null){
			responseUtil=new ResponseUtil(response);
		}
		return responseUtil;
	}
	
	@Override
	public void intercept(InvokeAction invokeAction) {
		try{
			CacheContext.activeUser();
			if(ConfigParameter.SYSTEMSTATUS){
				invokeAction.invoke();
			}else{
				result(IMsg._993);
			}
		}catch(Exception e){
			if(e.getClass().equals(SQLException.class)){
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				result(IMsg._999);
			}else if(e.getClass().equals(AppRuntimeException.class)){
				result(StringUtils.nullToIntZero(e.getMessage()));
			}else{
				Throwable cause=e.getCause();
				if(cause!=null){
					if(cause.getClass().equals(AppRuntimeException.class)){
						result(StringUtils.nullToIntZero(cause.getMessage()));
					}else{
						LogUtils.printLogError(cause.getMessage());
					}
				}else{
					LogUtils.printLogError(e.getMessage());
				}
			}
		}finally{
			if(result!=null){
				invokeAction.setActionResult(result);
			}
			CacheContext.inactiveUser();
		}
	}
	
	protected OperatorLog currentOperatorLog(Integer msg){
		if(operatorLog==null){
			operatorLog=new OperatorLog();
		}
		operatorLog.setBlwkIndex(StringUtils.nullToIntZero(ConfigParameter.BALANCED_WORKER_INDEX));
		operatorLog.setAction("["+msg+"]-->"+PropertiesUtils.getMessage(String.valueOf(msg)));
		operatorLog.setRequestIP(getRequestUtil().getRequestIP());
		operatorLog.setServletPath(request.getServletPath());
		operatorLog.setRequestTime(getRequestUtil().getRequestTime());
		operatorLog.setServerTime(TimeUtils.getSysTimeLong());
		//如果没有日志内容
		if(StringUtils.isEmpty(operatorLog.getContent())){
			StringBuilder logContent=new StringBuilder();
			for(String head:getRequestUtil().getHeaders().keySet()){
				logContent.append(head);
				logContent.append(Constants.COLON);
				logContent.append(getRequestUtil().getHeaderValue(head));
				logContent.append(Constants.SIGN_SPLITSTR);
			}
			operatorLog.setContent(logContent.toString());
		}
		return operatorLog;
	}
	/**
	 * 返回XML格式的信息给客户端
	 */
	protected IActionResult result(Integer msg){
		return result(msg, null);
	}
	
	protected IActionResult result(Object output){
		return result(IMsg._200,output);
	}
	
	protected IActionResult result(Integer msg,Object output){
		try{
			//记录操作日志
			operatorLogService.save(currentOperatorLog(msg));
			result=new XML(msg, output);
		}catch(AppRuntimeException e){
			result=new XML(Integer.parseInt(e.getMessage()),null);
		}
		return result;
	}
	
}