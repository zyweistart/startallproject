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
import com.hightern.system.model.Link;
import com.hightern.system.service.LinkService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("linkAction")
public class LinkAction extends BaseAction<Link> {
	
	/**
	 * 此LinkAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_LINK = "link_manager.htm";
	private static final String ACTION_SHOWADD_LINK = "link_showAdd.htm";
	private static final String FORWARD_MANAGER_LINK = "/system/manager_link.jsp";
	private static final String FORWARD_LIST_LINK = "/system/list_link.jsp";
	private static final String FORWARD_SHOWADD_LINK = "/system/add_link.jsp";
	private static final String FORWARD_SHOWEDIT_LINK = "/system/edit_link.jsp";
	private static final String FORWARD_DETAIL_LINK = "/system/detail_link.jsp";
	@Resource(name = "linkService")
	private LinkService linkService;
	private List<Link> links;
	private Link link;
	
	/**
	 * 撤消
	 * 
	 * @return
	 * @throws SystemExceptions
	 */
	public String undo() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不可为空！"); }
		final List<Long> ids = idsStringToList(this.getSelectedIds());
		if (ids != null && ids.size() > 0) {
			for (final Long id : ids) {
				final Link link = linkService.findById(id);
				link.setIsShow(new Integer("1"));// 收回
				linkService.save(link);
			}
		}
		this.setParameters("撤消成功！", LinkAction.ACTION_MANAGER_LINK);
		return Action.SUCCESS;
	}
	
	/**
	 * 发布
	 * 
	 * @return
	 * @throws SystemExceptions
	 */
	public String isSued() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不可为空！"); }
		final List<Long> ids = idsStringToList(this.getSelectedIds());
		if (ids != null && ids.size() > 0) {
			for (final Long id : ids) {
				final Link link = linkService.findById(id);
				link.setIsShow(new Integer("99"));// 收回
				linkService.save(link);
			}
		}
		this.setParameters("发布成功！", LinkAction.ACTION_MANAGER_LINK);
		return Action.SUCCESS;
	}
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != link) {
			if (link.getId() > 0) {
				this.setParameters("数据编辑成功！", LinkAction.ACTION_MANAGER_LINK);
			} else {
				this.setParameters("数据保存成功！", LinkAction.ACTION_SHOWADD_LINK);
			}
			if (link.getIsShow() == null) {
				link.setIsShow(1);
			}
			linkService.save(link);
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
		linkService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", LinkAction.ACTION_MANAGER_LINK);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (link == null) {
			link = new Link(request, null);
		}
		final String name = request.getParameter("name");
		if (name != null) {
			link.setName(name);
		}
		final String url = request.getParameter("url");
		if (url != null) {
			link.setUrl(url);
		}
		final String isShow = request.getParameter("isShow");
		if (isShow != null && !"".equals(isShow)) {
			link.setIsShow(Integer.parseInt(isShow));
		}
		links = linkService.paginated(link);
		this.setParameters(LinkAction.FORWARD_MANAGER_LINK);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (link == null) {
			link = new Link(request, null);
		}
		
		final String name = request.getParameter("name");
		if (name != null) {
			link.setName(name);
		}
		final String url = request.getParameter("url");
		if (url != null) {
			link.setUrl(url);
		}
		final String isShow = request.getParameter("isShow");
		if (isShow != null && !"".equals(isShow)) {
			link.setIsShow(Integer.parseInt(isShow));
		}
		links = linkService.paginated(link);
		this.setParameters(LinkAction.FORWARD_LIST_LINK);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		this.setParameters(LinkAction.FORWARD_SHOWADD_LINK);
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
		link = linkService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(LinkAction.FORWARD_SHOWEDIT_LINK);
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
		link = linkService.findById(getId());
		this.setParameters(LinkAction.FORWARD_DETAIL_LINK);
		return Action.SUCCESS;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public Link getLink() {
		if (link == null) {
			link = new Link();
		}
		return link;
	}
	
	public void setLink(Link link) {
		this.link = link;
	}
}