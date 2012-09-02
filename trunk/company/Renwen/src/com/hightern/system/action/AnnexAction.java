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
import com.hightern.system.model.Annex;
import com.hightern.system.service.AnnexService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("annexAction")
public class AnnexAction extends BaseAction<Annex> {
	
	/**
	 * 此AnnexAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_ANNEX = "annex_manager.htm";
	private static final String ACTION_SHOWADD_ANNEX = "annex_showAdd.htm";
	private static final String FORWARD_MANAGER_ANNEX = "/system/manager_annex.jsp";
	private static final String FORWARD_LIST_ANNEX = "/system/list_annex.jsp";
	private static final String FORWARD_SHOWADD_ANNEX = "/system/add_annex.jsp";
	private static final String FORWARD_SHOWEDIT_ANNEX = "/system/edit_annex.jsp";
	private static final String FORWARD_DETAIL_ANNEX = "/system/detail_annex.jsp";
	@Resource(name = "annexService")
	private AnnexService annexService;
	private List<Annex> annexs;
	private Annex annex;
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != annex) {
			if (annex.getId() > 0) {
				this.setParameters("数据编辑成功！", AnnexAction.ACTION_MANAGER_ANNEX);
			} else {
				this.setParameters("数据保存成功！", AnnexAction.ACTION_SHOWADD_ANNEX);
			}
			annexService.save(annex);
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
		annexService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", AnnexAction.ACTION_MANAGER_ANNEX);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (annex == null) {
			annex = new Annex(request, null);
		}
		annexs = annexService.paginated(annex);
		this.setParameters(AnnexAction.FORWARD_MANAGER_ANNEX);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (annex == null) {
			annex = new Annex(request, null);
		}
		annexs = annexService.paginated(annex);
		this.setParameters(AnnexAction.FORWARD_LIST_ANNEX);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		this.setParameters(AnnexAction.FORWARD_SHOWADD_ANNEX);
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
		annex = annexService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(AnnexAction.FORWARD_SHOWEDIT_ANNEX);
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
		annex = annexService.findById(getId());
		this.setParameters(AnnexAction.FORWARD_DETAIL_ANNEX);
		return Action.SUCCESS;
	}
	
	public List<Annex> getAnnexs() {
		return annexs;
	}
	
	public void setAnnexs(List<Annex> annexs) {
		this.annexs = annexs;
	}
	
	public Annex getAnnex() {
		if (annex == null) {
			annex = new Annex();
		}
		return annex;
	}
	
	public void setAnnex(Annex annex) {
		this.annex = annex;
	}
}