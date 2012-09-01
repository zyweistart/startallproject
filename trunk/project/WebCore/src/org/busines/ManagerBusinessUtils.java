package org.busines;

import org.framework.exception.AppRuntimeException;
import org.framework.request.RequestUtil;
import org.framework.utils.ACL;
import org.framework.utils.TimeUtils;
import org.message.IManager;
import org.message.ISystem;
import org.platform.manager.entity.Manager;
import org.platform.manager.entity.ManagerLoginLog;
import org.platform.manager.entity.Permission;
import org.platform.manager.entity.Role;

public class ManagerBusinessUtils {
	/**
	 * 检测账户登陆信息
	 */
	public static void checkManagerLoginLog(ManagerLoginLog managerLoginLog){
		if(managerLoginLog==null){
			//登陆信息不存在，请重新登陆
			throw new AppRuntimeException(ISystem._10101001);
		}else if(managerLoginLog.getOnlineStatus()){
			if(TimeUtils.compare_date(managerLoginLog.getInvalidTime(), TimeUtils.getSysTimeS())==-1){
				//登陆超时，请重新登陆
				throw new AppRuntimeException(ISystem._10101002);
			}
		}else{
			//登陆信息异常，请重新登陆
			throw new AppRuntimeException(ISystem._10101003);
		}
	}
	/**
	 * 检测登陆日志并对数据进行签名
	 */
	public static void checkManagerLoginlogSignature(RequestUtil reqeustUtil,ManagerLoginLog managerLoginLog){
		if(managerLoginLog!=null){
			reqeustUtil.signaturesHmacSHA1(managerLoginLog.getAccessKey());
		}else{
			//登陆信息不存在，请重新登陆
			throw new AppRuntimeException(ISystem._10101001);
		}
	}
	/**
	 * 检测管理员
	 */
	public static void checkManager(Manager manager){
		if(manager==null){
			//管理员信息不存在
			throw new AppRuntimeException(IManager._10401001);
		}
	}
	/**
	 * 检测角色
	 */
	public static void checkRole(Role role){
		if(role==null){
			//角色信息不存在
			throw new AppRuntimeException(IManager._10402003);
		}
	}
	/**
	 * 检测权限信息
	 */
	public static void checkPermission(Permission permission){
		if(permission==null){
			//权限信息不存在
			throw new AppRuntimeException(IManager._10403001);
		}
	}
	/**
	 * 检测当前操作是否授权
	 * @param manager
	 * 管理账户
	 * @param permission
	 * 权限类型
	 */
	public static void checkAuthorize(Manager manager,Permission permission){
		checkManager(manager);
		checkPermission(permission);
		ACL acl=new ACL();
		acl.setAuthorizeTotalValue(manager.getAcl());
		if(!acl.isAuthorize(permission.getValue())){
			//未授权的操作
			throw new AppRuntimeException(IManager._10401002);
		}
	}
}