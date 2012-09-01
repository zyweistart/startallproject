package org.framework.action;

import org.busines.ManagerBusinessUtils;
import org.busines.SystemBusinesUtils;
import org.framework.config.ConfigParameter;
import org.framework.exception.AppRuntimeException;
import org.framework.result.XML;
import org.framework.utils.PropertiesUtils;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;
import org.platform.manager.entity.ManagerLoginLog;
import org.platform.manager.entity.ManagerOperatorLog;
import org.platform.manager.entity.Permission;
import org.platform.manager.service.ManagerLoginLogService;
import org.platform.manager.service.ManagerOperatorLogService;
import org.platform.manager.service.PermissionService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Resource;
import org.zyweistartframework.controller.IActionResult;

/**
 * 管理平台操作基类
 * @author Start
 */
public class BaseMangerAction extends RootAction {
	
	protected ManagerLoginLog managerLoginLog;
	protected ManagerOperatorLog managerOperatorLog;
	
	@Resource
	protected PermissionService permissionService;
	@Resource
	protected ManagerLoginLogService managerLoginLogService;
	@Resource
	protected ManagerOperatorLogService managerOperatorLogService;
	/**
	 * 获取当前的登陆日志信息
	 */
	protected ManagerLoginLog currentManagerLoginLog(){
		if(managerLoginLog==null){
			String accessId=getRequestUtil().getAccessID();
			SystemBusinesUtils.checkAccessID(accessId);
			managerLoginLog=managerLoginLogService.loadByAccessID(accessId);
			ManagerBusinessUtils.checkManagerLoginLog(managerLoginLog);
			managerLoginLogService.activationManagerLoginLog(accessId);
		}
		return managerLoginLog;
	}
	/**
	 * 获取当前管理者的操作日志
	 */
	protected ManagerOperatorLog currentManagerOperatorLog(Integer msg){
		if(managerOperatorLog==null){
			managerOperatorLog=new ManagerOperatorLog();
		}
		managerOperatorLog.setBlwkIndex(StringUtils.nullToIntZero(ConfigParameter.BALANCED_WORKER_INDEX));
		managerOperatorLog.setAction("["+msg+"]-->"+PropertiesUtils.getMessage(String.valueOf(msg)));
		managerOperatorLog.setRequestIP(getRequestUtil().getRequestIP());
		managerOperatorLog.setServletPath(request.getServletPath());
		managerOperatorLog.setRequestTime(getRequestUtil().getRequestTime());
		managerOperatorLog.setServerTime(TimeUtils.getSysTimeLong());
		//如果没有日志内容
		if(StringUtils.isEmpty(managerOperatorLog.getContent())){
			StringBuilder logContent=new StringBuilder();
			for(String head:getRequestUtil().getHeaders().keySet()){
				logContent.append(head);
				logContent.append(Constants.COLON);
				logContent.append(getRequestUtil().getHeaderValue(head));
				logContent.append(Constants.SIGN_SPLITSTR);
			}
			managerOperatorLog.setContent(logContent.toString());
		}
		managerOperatorLog.setManagerLoginLog(currentManagerLoginLog());
		return managerOperatorLog;
	}
	
	@Override
	protected IActionResult result(Integer msg,Object output){
		if(managerLoginLog==null){
			super.result(msg,output);
		}else{
			try{
				//记录操作日志
				managerOperatorLogService.save(currentManagerOperatorLog(msg));
				result=new XML(msg, output);
			}catch(AppRuntimeException e){
				result=new XML(Integer.parseInt(e.getMessage()),null);
			}
		}
		return result;
	}
	/**
	 * <pre>
	 * 授权验证,必须在获取注入数据之后进行调用
	 * 1.验证数据签名
	 * 2.验证授权信息
	 * </pre>
	 */
	protected void authorizedtoVerify(){
		//数据签名
		ManagerBusinessUtils.checkManagerLoginlogSignature(getRequestUtil(),currentManagerLoginLog());
		//获取当前请求的权限信息
		Permission permission=permissionService.loadPermissionByAction(request.getServletPath());
		//授权验证
		ManagerBusinessUtils.checkAuthorize(currentManagerLoginLog().getManager(),permission);
	}
	
}