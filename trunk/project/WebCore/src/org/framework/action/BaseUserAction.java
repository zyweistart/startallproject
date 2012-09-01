package org.framework.action;

import org.busines.SystemBusinesUtils;
import org.busines.UserBusinesUtils;
import org.framework.config.ConfigParameter;
import org.framework.exception.AppRuntimeException;
import org.framework.result.XML;
import org.framework.utils.PropertiesUtils;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;
import org.platform.user.entity.UserLoginLog;
import org.platform.user.entity.UserOperatorLog;
import org.platform.user.service.UserLoginLogService;
import org.platform.user.service.UserOperatorLogService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Resource;
import org.zyweistartframework.controller.IActionResult;

/**
 * 用户平台操作基类
 * @author Start
 */
public class BaseUserAction extends RootAction {
	
	protected UserLoginLog userLoginLog;
	protected UserOperatorLog userOperatorLog;
	
	@Resource
	protected UserLoginLogService userLoginLogService;
	@Resource
	protected UserOperatorLogService userOperatorLogService;
	/**
	 * 获取当前的登陆日志信息
	 */
	protected UserLoginLog currentUserLoginLog(){
		if(userLoginLog==null){
			String accessId=getRequestUtil().getAccessID();
			SystemBusinesUtils.checkAccessID(accessId);
			userLoginLog=userLoginLogService.loadByAccessID(accessId);
			UserBusinesUtils.checkUserLoginLog(userLoginLog);
			userLoginLogService.activationUserLoginLog(accessId);
		}
		return userLoginLog;
	}
	/**
	 * 获取当前用户的操作日志
	 */
	protected UserOperatorLog currentUserOperatorLog(Integer msg){
		if(userOperatorLog==null){
			userOperatorLog=new UserOperatorLog();
		}
		userOperatorLog.setBlwkIndex(StringUtils.nullToIntZero(ConfigParameter.BALANCED_WORKER_INDEX));
		userOperatorLog.setAction("["+msg+"]-->"+PropertiesUtils.getMessage(String.valueOf(msg)));
		userOperatorLog.setRequestIP(getRequestUtil().getRequestIP());
		userOperatorLog.setServletPath(request.getServletPath());
		userOperatorLog.setRequestTime(getRequestUtil().getRequestTime());
		userOperatorLog.setServerTime(TimeUtils.getSysTimeLong());
		//如果没有日志内容
		if(StringUtils.isEmpty(userOperatorLog.getContent())){
			StringBuilder logContent=new StringBuilder();
			for(String head:getRequestUtil().getHeaders().keySet()){
				logContent.append(head);
				logContent.append(Constants.COLON);
				logContent.append(getRequestUtil().getHeaderValue(head));
				logContent.append(Constants.SIGN_SPLITSTR);
			}
			userOperatorLog.setContent(logContent.toString());
		}
		userOperatorLog.setUserLoginLog(currentUserLoginLog());
		return userOperatorLog;
	}
	
	@Override
	protected IActionResult result(Integer msg,Object output){
		if(userLoginLog==null){
			super.result(msg,output);
		}else{
			try{
				//记录操作日志
				userOperatorLogService.save(currentUserOperatorLog(msg));
				result=new XML(msg, output);
			}catch(AppRuntimeException e){
				result=new XML(Integer.parseInt(e.getMessage()),null);
			}
		}
		return result;
	}
	
}