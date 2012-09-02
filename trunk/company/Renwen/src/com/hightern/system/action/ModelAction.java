/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.JsonUtil;
import com.hightern.system.model.Model;
import com.hightern.system.service.ModelService;
import com.opensymphony.xwork2.Action;

/**
 * 菜单管理
 * 
 * @author Stone
 */
@Scope("prototype")
@Controller("modelAction")
public class ModelAction extends BaseAction<Model> {
	
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_MODEL = "model_manager.htm";
	private static final String ACTION_SHOWADD_MODEL = "model_showAdd.htm";
	private static final String FORWARD_MANAGER_MODEL = "/system/manager_model.jsp";
	private static final String FORWARD_LIST_MODEL = "/system/list_model.jsp";
	private static final String FORWARD_SHOWADD_MODEL = "/system/add_model.jsp";
	private static final String FORWARD_SHOWEDIT_MODEL = "/system/edit_model.jsp";
	private static final String FORWARD_DETAIL_MODEL = "/system/detail_model.jsp";
	private static final String FORWARD_TREE_MODEL = "/system/tree_model.jsp";
	@Resource(name = "modelService")
	private ModelService modelService;
	private List<Model> models;
	private Model model;
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != model) {
			final Model mod = modelService.findById(model.getParentId());
			if (mod != null) {
				model.setGrade(mod.getGrade() + 1);
			} else {
				model.setGrade(new Integer("0"));
			}
			if (model.getId() > 0) {
				this.setParameters("数据编辑成功！", ModelAction.ACTION_MANAGER_MODEL);
			} else {
				this.setParameters("数据保存成功！", ModelAction.ACTION_SHOWADD_MODEL);
			}
			modelService.save(model);
			return Action.SUCCESS;
		} else {
			return Action.INPUT;
		}
	}
	
	/**
	 * 树形菜单
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String tree() throws SystemExceptions {
		models = modelService.queryForObject("select o from model o ", null);
		this.setParameters(ModelAction.FORWARD_TREE_MODEL);
		return Action.SUCCESS;
	}
	
	/**
	 * 根据菜单编号取得菜单名称
	 * 
	 * @return null
	 * @throws SystemExceptions
	 */
	public String ajax() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (isNullOrEmptyString(this.getId())) { throw new SystemExceptions("菜单编号不可为空！"); }
		model = modelService.findById(this.getId());
		response.getWriter().print(JsonUtil.getJsonStringJavaPOJO(model));
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 删除操作
	 * 
	 * @return SUCESS
	 * @throws SystemExceptions
	 */
	public String delete() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不能为空！"); }
		modelService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", ModelAction.ACTION_MANAGER_MODEL);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		final String grade = request.getParameter("grade");
		if (model == null) {
			model = new Model(request, null);
		}
		model.setName(request.getParameter("name"));
		if (grade != null && grade.length() > 0) {
			model.setGrade(Integer.valueOf(grade));
		}
		model.setScription(request.getParameter("scription"));
		models = modelService.paginated(model);
		this.setParameters(ModelAction.FORWARD_MANAGER_MODEL);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 */
	public String list() throws SystemExceptions {
		final String grade = request.getParameter("grade");
		if (model == null) {
			model = new Model(request, null);
		}
		model.setName(request.getParameter("name"));
		if (grade != null && grade.length() > 0) {
			model.setGrade(Integer.valueOf(grade.trim()));
		}
		model.setScription(request.getParameter("scription"));
		models = modelService.paginated(model);
		this.setParameters(ModelAction.FORWARD_LIST_MODEL);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		this.setParameters(ModelAction.FORWARD_SHOWADD_MODEL);
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
		model = modelService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(ModelAction.FORWARD_SHOWEDIT_MODEL);
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
		model = modelService.findById(getId());
		this.setParameters(ModelAction.FORWARD_DETAIL_MODEL);
		return Action.SUCCESS;
	}
	
	public Model getModel() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public List<Model> getModels() {
		return models;
	}
	
	public void setModels(List<Model> models) {
		this.models = models;
	}
}
