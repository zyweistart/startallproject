package org.platform.manager.action;

import java.util.List;

import org.busines.ManagerBusinessUtils;
import org.framework.action.BaseMangerAction;
import org.framework.config.Variable;
import org.framework.utils.ACL;
import org.framework.utils.StringUtils;
import org.jdom.Element;
import org.message.IManager;
import org.message.IMsg;
import org.platform.manager.entity.Permission;
import org.platform.manager.entity.Role;
import org.platform.manager.service.RoleService;
import org.zyweistartframework.context.annotation.Controller;
import org.zyweistartframework.context.annotation.Resource;

@Controller("role")
public final class RoleAction extends BaseMangerAction {
	
	@Resource
	private RoleService roleService;
	/**
	 * 创建
	 */
	@SuppressWarnings("unchecked")
	public void create(){
		Role role=getRequestUtil().getTextDataUpload(Role.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(role.getName())){
			//角色名称不能为空
			result(IManager._10402001);
		}else{
			if(roleService.isPresenceByName(role.getName())){
				//当前角色已经存在
				result(IManager._10402002);
			}else{
				ACL acl=new ACL();
				Element root=getRequestUtil().getXMLDocumentRoot();
				List<Element> eles=root.getChildren(Variable.PERMISSION);
				String[] permissionActions=new String[eles.size()];
				for(int i=0;i<eles.size();i++){
					permissionActions[i]=eles.get(i).getValue().trim();
				}
				role.setPermissions(permissionService.loadPermissionsByActions(permissionActions));
				for(Permission per:role.getPermissions()){
					//设置当前添加的授权值 
					acl.setAuthorize(per.getValue(), true);
				}
				role.setAcl(acl.getAclState());
				roleService.save(role);
				result(IMsg._200);
			}
		}
	}
	/**
	 * 角色授权
	 */
	@SuppressWarnings("unchecked")
	public void authorize(){
		Role role=getRequestUtil().getTextDataUpload(Role.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(role.getName())){
			//角色名称不能为空
			result(IManager._10402001);
		}else{
			role=roleService.loadRoleByName(role.getName());
			ManagerBusinessUtils.checkRole(role);
			ACL acl=new ACL();
			//设置已经存在的授权总值
			acl.setAuthorizeTotalValue(role.getAcl());
			Element root=getRequestUtil().getXMLDocumentRoot();
			List<Element> eles=root.getChildren(Variable.PERMISSION);
			String[] permissionActions=new String[eles.size()];
			for(int i=0;i<eles.size();i++){
				permissionActions[i]=eles.get(i).getValue().trim();
			}
			role.setPermissions(permissionService.loadPermissionsByActions(permissionActions));
			for(Permission per:role.getPermissions()){
				//设置当前添加的授权值 
				acl.setAuthorize(per.getValue(), true);
			}
			role.setAcl(acl.getAclState());
			roleService.save(role);
			result(IMsg._200);
		}
	}
	
	public void remove(){
		Role role=getRequestUtil().getPostData(Role.class);
		authorizedtoVerify();
		if(StringUtils.isEmpty(role.getName())){
			//角色编号不能为空
			result(IManager._10402001);
		}else{
			role=roleService.loadRoleByName(role.getName());
			ManagerBusinessUtils.checkRole(role);
			roleService.remove(role);
			result(IMsg._200);
		}
	}
	
}