package org.platform.manager.action;

import org.busines.ManagerBusinessUtils;
import org.framework.action.BaseMangerAction;
import org.framework.utils.StringUtils;
import org.message.IManager;
import org.message.IMsg;
import org.platform.manager.entity.Permission;
import org.zyweistartframework.context.annotation.Controller;

@Controller("permission")
public final class PermissionAction extends BaseMangerAction {
	
	public void create(){
		Permission permission=getRequestUtil().getPostData(Permission.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(permission.getName())){
			//权限名称不能为空
			result(IManager._10403004);
		}else if(StringUtils.isEmpty(permission.getAction())){
			//权限编号不能为空
			result(IManager._10403002);
		}else{
			if(permissionService.isPresenceByAction(permission.getAction())){
				//当前权限已经存在
				result(IManager._10403003);
			}else{
				permissionService.save(permission);
				result(IMsg._200);
			}
		}
	}
	
	public void remove(){
		Permission permission=getRequestUtil().getPostData(Permission.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(permission.getName())){
			//权限名称不能为空
			result(IManager._10403004);
		}else if(StringUtils.isEmpty(permission.getAction())){
			//权限编号不能为空
			result(IManager._10403002);
		}else{
			permission=permissionService.loadPermissionByAction(permission.getAction());
			ManagerBusinessUtils.checkPermission(permission);
			permissionService.remove(permission);
			result(IMsg._200);
		}
	}
	
}