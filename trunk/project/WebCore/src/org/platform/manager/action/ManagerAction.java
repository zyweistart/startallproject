package org.platform.manager.action;

import java.util.List;

import org.busines.ManagerBusinessUtils;
import org.framework.action.BaseMangerAction;
import org.framework.config.Variable;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.ACL;
import org.framework.utils.StringCheck;
import org.framework.utils.StringUtils;
import org.jdom.Element;
import org.message.IManager;
import org.message.IMsg;
import org.message.IUser;
import org.platform.manager.entity.Manager;
import org.platform.manager.entity.ManagerLoginLog;
import org.platform.manager.entity.Permission;
import org.platform.manager.entity.Role;
import org.platform.manager.service.ManagerService;
import org.platform.manager.service.RoleService;
import org.zyweistartframework.context.annotation.Controller;
import org.zyweistartframework.context.annotation.Resource;

@Controller("manager")
public final class ManagerAction extends BaseMangerAction {
	
	@Resource
	private RoleService roleService;
	@Resource
	private ManagerService managerService;
	
	public void login(){
		Manager manager=getRequestUtil().getGetData(Manager.class);
		if(StringUtils.isEmpty(manager.getUsername())){
			//用户名不能为空
			result(IManager._10401003);
		}else{
			String ip=getRequestUtil().getRequestIP();
			Manager m=managerService.loadManagerByUserName(manager.getUsername());
			ManagerBusinessUtils.checkManager(m);
			try{
				//使用当前登录用户的密码进行签名
				getRequestUtil().signaturesHmacSHA1(StringUtils.doKeyDecrypt(m.getPassword()));
			}catch(AppRuntimeException e){
				//签名不正确或密码有误
				result(IUser._10201016);
				return;
			}
			managerLoginLog=new ManagerLoginLog();
			managerLoginLog.setId(StringUtils.random());
			//生成访问ID
			managerLoginLog.setAccessId(StringUtils.random());
			//生成数据访问签名键
			managerLoginLog.setAccessKey(StringUtils.random(managerLoginLog.getAccessId()));
			managerLoginLog.setLoginIP(ip);
			managerLoginLog.setManager(m);
			managerLoginLogService.save(managerLoginLog);
			result(managerLoginLog);
		}
	}
	/**
	 * 退出登陆
	 */
	public void logout(){
		getRequestUtil().getGetData(null);
		ManagerBusinessUtils.checkManagerLoginlogSignature(getRequestUtil(),currentManagerLoginLog());
		managerLoginLogService.logout(currentManagerLoginLog());
		result(IMsg._200);
	}
	/**
	 * 创建管理员
	 */
	@SuppressWarnings("unchecked")
	public void create(){
		Manager manager=getRequestUtil().getTextDataUpload(Manager.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(manager.getUsername())){
			//用户名不能为空
			result(IManager._10401003);
		}else if(StringUtils.isEmpty(manager.getPassword())){
			//密码不能为空
			result(IUser._10201009);
		}else if(!StringCheck.checkMd5(manager.getPassword())){
			//密码格式不正确
			result(IUser._10201010);
		}else{
			if(managerService.isPresenceByName(manager.getUsername())){
				//当前用户已存在
				result(IManager._10401004);
			}else{
				//对密码再进行DES加密
				manager.setPassword(StringUtils.doKeyEncrypt(manager.getPassword()));
				ACL acl=new ACL();
				Element root=getRequestUtil().getXMLDocumentRoot();
				
				List<Element> eles=root.getChildren(Variable.ROLE);
				String[] rolesNames=new String[eles.size()];
				for(int i=0;i<eles.size();i++){
					rolesNames[i]=eles.get(i).getValue().trim();
				}
				manager.setRoles(roleService.loadRolesByNames(rolesNames));
				int aclVal=0;
				for(Role role:manager.getRoles()){
					aclVal+=role.getAcl();
				}
				acl.setAuthorizeTotalValue(aclVal);
				
				eles=root.getChildren(Variable.PERMISSION);
				String[] permissionCodes=new String[eles.size()];
				for(int i=0;i<eles.size();i++){
					permissionCodes[i]=eles.get(i).getValue().trim();
				}
				manager.setPermissions(permissionService.loadPermissionsByActions(permissionCodes));
				for(Permission per:manager.getPermissions()){
					//设置当前添加的授权值 
					acl.setAuthorize(per.getValue(), true);
				}
				manager.setAcl(acl.getAclState());
				managerService.save(manager);
				result(IMsg._200);
			}
		}
	}
	/**
	 * 管理员授权
	 */
	@SuppressWarnings("unchecked")
	public void authorize(){
		Manager manager=getRequestUtil().getTextDataUpload(Manager.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(manager.getUsername())){
			//用户名不能为空
			result(IManager._10401003);
		}else{
			manager=managerService.loadManagerByUserName(manager.getUsername());
			ManagerBusinessUtils.checkManager(manager);
			Element root=getRequestUtil().getXMLDocumentRoot();
			List<Element> eles=root.getChildren(Variable.ROLE);
			String[] rolesNames=new String[eles.size()];
			for(int i=0;i<eles.size();i++){
				rolesNames[i]=eles.get(i).getValue().trim();
			}
			manager.setRoles(roleService.loadRolesByNames(rolesNames));
			int aclVal=manager.getAcl();
			for(Role role:manager.getRoles()){
				aclVal+=role.getAcl();
			}
			ACL acl=new ACL();
			acl.setAuthorizeTotalValue(aclVal);
			eles=root.getChildren(Variable.PERMISSION);
			String[] permissionActions=new String[eles.size()];
			for(int i=0;i<eles.size();i++){
				permissionActions[i]=eles.get(i).getValue().trim();
			}
			manager.setPermissions(permissionService.loadPermissionsByActions(permissionActions));
			for(Permission per:manager.getPermissions()){
				//设置当前添加的授权值 
				acl.setAuthorize(per.getValue(), true);
			}
			manager.setAcl(acl.getAclState());
			managerService.save(manager);
			result(IMsg._200);
		}
	}

}