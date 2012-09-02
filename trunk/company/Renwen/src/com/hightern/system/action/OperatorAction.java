/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.kernel.util.GenerateCode;
import com.hightern.kernel.util.MD5;
import com.hightern.system.model.Operator;
import com.hightern.system.model.Posts;
import com.hightern.system.model.Role;
import com.hightern.system.service.OperatorService;
import com.hightern.system.service.PostsService;
import com.hightern.system.service.RoleService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("operatorAction")
public class OperatorAction extends BaseAction<Operator> {
	
	/**
	 * 此OperatorAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_OPERATOR = "operator_manager.htm";
	private static final String ACTION_SHOWADD_OPERATOR = "operator_showAdd.htm";
	private static final String FORWARD_MANAGER_OPERATOR = "/system/manager_operator.jsp";
	private static final String FORWARD_LIST_OPERATOR = "/system/list_operator.jsp";
	private static final String FORWARD_SHOWADD_OPERATOR = "/system/add_operator.jsp";
	private static final String FORWARD_SHOWEDIT_OPERATOR = "/system/edit_operator.jsp";
	private static final String FORWARD_DETAIL_OPERATOR = "/system/detail_operator.jsp";
	@Resource(name = "operatorService")
	private OperatorService operatorService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "postsService")
	private PostsService postsService;
	private List<Operator> operators;
	private List<Posts> postss;
	private List<Role> roles;
	private Operator operator;
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != operator) {
			if (operator.getId() > 0) {
				if (!operator.getRepassword().equals(operator.getPassword())) {
					operator.setPassword(MD5.calcMD5(operator.getRepassword()));
				}
				this.setParameters("数据编辑成功！", OperatorAction.ACTION_MANAGER_OPERATOR);
			} else {
				if (operator.getIdCard() != null) {
					operator.setIdCard("'" + operator.getIdCard());
				}
				operator.setPassword(MD5.calcMD5(operator.getPassword()));
				this.setParameters("数据保存成功！", OperatorAction.ACTION_SHOWADD_OPERATOR);
			}
			operator.setCssStyle("default");
			operatorService.save(operator);
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
		operatorService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", OperatorAction.ACTION_MANAGER_OPERATOR);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (operator == null) {
			operator = new Operator(request, null);
		}
		operator.setTrueName(request.getParameter("trueName"));
		operator.setLoginName(request.getParameter("loginName"));
		operator.setIdCard(request.getParameter("idCard"));
		operators = operatorService.paginated(operator);
		this.setParameters(OperatorAction.FORWARD_MANAGER_OPERATOR);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (operator == null) {
			operator = new Operator(request, null);
		}
		operator.setTrueName(request.getParameter("trueName"));
		operator.setLoginName(request.getParameter("loginName"));
		operator.setIdCard(request.getParameter("idCard"));
		operators = operatorService.paginated(operator);
		this.setParameters(OperatorAction.FORWARD_LIST_OPERATOR);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		if (operator == null) {
			operator = new Operator();
		}
		operator.setCreateDate(DateHelper.getDate());
		operator.setCode(GenerateCode.genRandCode());
		postss = postsService.findAll();
		roles = roleService.findAll();
		this.setParameters(OperatorAction.FORWARD_SHOWADD_OPERATOR);
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
		final List<Role> temp = new ArrayList<Role>();
		operator = operatorService.findById(idsStringToList(getSelectedIds()).get(0));
		roles = roleService.findAll();
		postss = postsService.findAll();
		if (operator.getRoles() != null) {
			final String[] code = operator.getRoles().split(",");
			if (null != code) {
				for (final String element : code) {
					final String ids = element;
					if (null != ids && !"".equals(ids)) {
						final List<Role> temps = roleService.queryForObject("select o from role o where o.roleCode='" + ids + "'", null);
						if (temps != null && temps.size() > 0) {
							for (final Role role : temps) {
								roles.remove(role);
								temp.add(role);
							}
						}
					}
					
				}
			}
		}
		request.setAttribute("selectedRoles", temp);
		this.setParameters(OperatorAction.FORWARD_SHOWEDIT_OPERATOR);
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
		operator = operatorService.findById(getId());
		this.setParameters(OperatorAction.FORWARD_DETAIL_OPERATOR);
		return Action.SUCCESS;
	}
	
	public List<Operator> getOperators() {
		return operators;
	}
	
	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}
	
	public Operator getOperator() {
		if (operator == null) {
			operator = new Operator();
		}
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<Posts> getPostss() {
		return postss;
	}
	
	public void setPostss(List<Posts> postss) {
		this.postss = postss;
	}
	
}
