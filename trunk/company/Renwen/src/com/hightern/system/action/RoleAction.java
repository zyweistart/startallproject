/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.kernel.util.GenerateCode;
import com.hightern.system.model.Role;
import com.hightern.system.service.RoleService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("roleAction")
public class RoleAction extends BaseAction<Role> {
	
	/**
	 * 此RoleAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_ROLE = "role_manager.htm";
	private static final String ACTION_SHOWADD_ROLE = "role_showAdd.htm";
	private static final String FORWARD_MANAGER_ROLE = "/system/manager_role.jsp";
	private static final String FORWARD_LIST_ROLE = "/system/list_role.jsp";
	private static final String FORWARD_SHOWADD_ROLE = "/system/add_role.jsp";
	private static final String FORWARD_SHOWEDIT_ROLE = "/system/edit_role.jsp";
	private static final String FORWARD_DETAIL_ROLE = "/system/detail_role.jsp";
	@Resource(name = "roleService")
	private RoleService roleService;
	private List<Role> roles;
	private Role role;
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != role) {
			if (role.getId() > 0) {
				this.setParameters("数据编辑成功！", RoleAction.ACTION_MANAGER_ROLE);
			} else {
				this.setParameters("数据保存成功！", RoleAction.ACTION_SHOWADD_ROLE);
			}
			roleService.save(role);
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除操作
	 * 
	 * @return SUCESS
	 * @throws SystemExceptions
	 */
	public String delete() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不能为空！"); }
		roleService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", RoleAction.ACTION_MANAGER_ROLE);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (role == null) {
			role = new Role(request, null);
		}
		role.setRoleName(request.getParameter("roleName"));
		role.setCreateName(request.getParameter("createName"));
		role.setCreateDate(request.getParameter("createDate"));
		roles = roleService.paginated(role);
		this.setParameters(RoleAction.FORWARD_MANAGER_ROLE);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (role == null) {
			role = new Role(request, null);
		}
		role.setRoleName(request.getParameter("roleName"));
		role.setCreateName(request.getParameter("createName"));
		role.setCreateDate(request.getParameter("createDate"));
		roles = roleService.paginated(role);
		this.setParameters(RoleAction.FORWARD_LIST_ROLE);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		if (role == null) {
			role = new Role();
		}
		role.setRoleCode(GenerateCode.getrandstring(16));
		role.setCreateDate(DateHelper.getDate());
		this.setParameters(RoleAction.FORWARD_SHOWADD_ROLE);
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showEdit() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不能为空！"); }
		role = roleService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(RoleAction.FORWARD_SHOWEDIT_ROLE);
		return Action.SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String detail() throws SystemExceptions {
		if (isNullOrEmptyString(getId())) { throw new SystemExceptions("编号不能为空！"); }
		role = roleService.findById(getId());
		this.setParameters(RoleAction.FORWARD_DETAIL_ROLE);
		return Action.SUCCESS;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public Role getRole() {
		if (role == null) {
			role = new Role();
		}
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
