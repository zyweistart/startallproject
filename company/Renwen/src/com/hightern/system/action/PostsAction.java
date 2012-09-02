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
import com.hightern.system.model.Posts;
import com.hightern.system.service.PostsService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("postsAction")
public class PostsAction extends BaseAction<Posts> {
	
	/**
	 * 此PostsAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_POSTS = "posts_manager.htm";
	private static final String ACTION_SHOWADD_POSTS = "posts_showAdd.htm";
	private static final String FORWARD_MANAGER_POSTS = "/system/manager_posts.jsp";
	private static final String FORWARD_LIST_POSTS = "/system/list_posts.jsp";
	private static final String FORWARD_SHOWADD_POSTS = "/system/add_posts.jsp";
	private static final String FORWARD_SHOWEDIT_POSTS = "/system/edit_posts.jsp";
	private static final String FORWARD_DETAIL_POSTS = "/system/detail_posts.jsp";
	@Resource(name = "postsService")
	private PostsService postsService;
	private List<Posts> postss;
	private Posts posts;
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != posts) {
			if (posts.getId() > 0) {
				this.setParameters("数据编辑成功！", PostsAction.ACTION_MANAGER_POSTS);
			} else {
				this.setParameters("数据保存成功！", PostsAction.ACTION_SHOWADD_POSTS);
			}
			postsService.save(posts);
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
		postsService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", PostsAction.ACTION_MANAGER_POSTS);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (posts == null) {
			posts = new Posts(request, null);
		}
		posts.setPostName(request.getParameter("postName"));
		posts.setCreateName(request.getParameter("createName"));
		posts.setCreateDate(request.getParameter("createDate"));
		final String status = request.getParameter("status");
		if (status != null && status.length() > 0) {
			posts.setStatus(Integer.valueOf(status));
		}
		postss = postsService.paginated(posts);
		this.setParameters(PostsAction.FORWARD_MANAGER_POSTS);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (posts == null) {
			posts = new Posts(request, null);
		}
		posts.setPostName(request.getParameter("postName"));
		posts.setCreateName(request.getParameter("createName"));
		posts.setCreateDate(request.getParameter("createDate"));
		final String status = request.getParameter("status");
		if (status != null && status.length() > 0) {
			posts.setStatus(Integer.valueOf(status));
		}
		postss = postsService.paginated(posts);
		this.setParameters(PostsAction.FORWARD_LIST_POSTS);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		if (posts == null) {
			posts = new Posts();
		}
		posts.setPostCode(GenerateCode.getrandstring(16));
		posts.setCreateDate(DateHelper.getDate());
		this.setParameters(PostsAction.FORWARD_SHOWADD_POSTS);
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
		posts = postsService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(PostsAction.FORWARD_SHOWEDIT_POSTS);
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
		posts = postsService.findById(getId());
		this.setParameters(PostsAction.FORWARD_DETAIL_POSTS);
		return Action.SUCCESS;
	}
	
	public List<Posts> getPostss() {
		return postss;
	}
	
	public void setPostss(List<Posts> postss) {
		this.postss = postss;
	}
	
	public Posts getPosts() {
		if (posts == null) {
			posts = new Posts();
		}
		return posts;
	}
	
	public void setPosts(Posts posts) {
		this.posts = posts;
	}
}