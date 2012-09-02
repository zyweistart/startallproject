package com.hightern.system.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.MD5;
import com.hightern.system.model.Model;
import com.hightern.system.model.Operator;
import com.hightern.system.model.Posts;
import com.hightern.system.model.Role;
import com.hightern.system.service.ModelService;
import com.hightern.system.service.OperatorService;
import com.hightern.system.service.PostsService;
import com.hightern.system.service.RoleService;
import com.hightern.system.util.ObjectToXml;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("authorityAction")
public class AuthorityAction extends BaseAction<Operator> {
	
	/**
	 * 权限设置
	 */
	private static final long serialVersionUID = -3820237894092941467L;
	private static final String FORWARD_MANAGER_AUTH = "/system/manager_authority.jsp";
	
	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "operatorService")
	private OperatorService operatorService;
	
	@Resource(name = "postsService")
	private PostsService postsService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	private List<Model> models;
	
	private List<Posts> postss;
	
	private String typeName;
	
	private String typeCode;
	
	@Override
	public String execute() throws Exception {
		models = modelService.getChildrenModels(0l);
		postss = postsService.queryForObject("select o from posts o", null);
		this.setParameters(AuthorityAction.FORWARD_MANAGER_AUTH);
		return Action.SUCCESS;
	}
	
	/**
	 * 保存修改过的权限
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String save() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (isNullOrEmptyString(this.getTypeCode()) || isNullOrEmptyString(this.getSelectedIds()) || isNullOrEmptyString(this.getTypeName())) { throw new SystemExceptions(
				"类型编号不可为空!"); }
		if ("posts".equals(this.getTypeCode())) {
			final List<Posts> posts = postsService.queryForObject("select o from posts o where o.postCode='" + this.getTypeName() + "'", null);
			if (posts != null && posts.size() > 0) {
				for (final Posts post : posts) {
					post.setModels(this.getSelectedIds());
					postsService.save(post);
					break;
				}
			}
		} else if ("roles".equals(this.getTypeCode())) {
			final List<Role> roles = roleService.queryForObject("select o from role o where o.roleCode='" + this.getTypeName() + "'", null);
			if (roles != null && roles.size() > 0) {
				for (final Role role : roles) {
					role.setModels(this.getSelectedIds());
					roleService.save(role);
					break;
				}
			}
		} else {
			final List<Operator> operators = operatorService.queryForObject("select o from operator o where o.code='" + this.getTypeName() + "'",
					null);
			if (operators != null && operators.size() > 0) {
				for (final Operator operator : operators) {
					operator.setModels(this.getSelectedIds());
					operatorService.save(operator);
					break;
				}
			}
		}
		response.getWriter().print("success");
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 权限分配时所用
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String getModelsToJson() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (isNullOrEmptyString(this.getTypeCode()) || isNullOrEmptyString(this.getSelectedIds())) { throw new SystemExceptions("类型编号不可为空!"); }
		if ("posts".equals(this.getTypeCode())) {
			final List<Posts> posts = postsService.queryForObject("select o from posts o where o.postCode='" + this.getSelectedIds() + "'", null);
			if (posts != null && posts.size() > 0) {
				for (final Posts post : posts) {
					response.getWriter().print(post.getModels());
					response.getWriter().close();
					break;
				}
			}
		} else if ("roles".equals(this.getTypeCode())) {
			final List<Role> roles = roleService.queryForObject("select o from role o where o.roleCode='" + this.getSelectedIds() + "'", null);
			if (roles != null && roles.size() > 0) {
				for (final Role role : roles) {
					response.getWriter().print(role.getModels());
					response.getWriter().close();
					break;
				}
			}
		} else {
			final List<Operator> operators = operatorService.queryForObject("select o from operator o where o.code='" + this.getSelectedIds() + "'",
					null);
			if (operators != null && operators.size() > 0) {
				for (final Operator operator : operators) {
					response.getWriter().print(operator.getModels());
					response.getWriter().close();
					break;
				}
			}
		}
		return null;
	}
	
	/**
	 * ajax方式取数据
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String ajax() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		String XML = null;
		if ("posts".equals(this.getTypeCode())) {
			postss = postsService.findAll();
			XML = ObjectToXml.PostsXML(postss);
		} else if ("roles".equals(this.getTypeCode())) {
			final List<Role> roles = roleService.findAll();
			XML = ObjectToXml.RoleXML(roles);
		} else {
			final List<Operator> datas = operatorService.findAll();
			XML = ObjectToXml.OperatorXML(datas);
		}
		response.getWriter().print(XML);
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 更新主题样式
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String changeTheme() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		final String cssStyle = request.getParameter("cssStyle");
		if (cssStyle != null && cssStyle.length() > 0) {
			final Operator operator = (Operator) session.get("currentUser");
			operator.setCssStyle(cssStyle);
			operatorService.save(operator);
			response.getWriter().print("success");
		}
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 修改登陆密码
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String changePassword() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		final String oldPass = request.getParameter("oldPassword");
		final String newPass = request.getParameter("newPassword");
		final Operator operator = (Operator) session.get("currentUser");
		if (operator.getPassword().equals(MD5.calcMD5(oldPass))) {
			if (newPass != null && newPass.length() > 0) {
				operator.setPassword(MD5.calcMD5(newPass));
			}
			operatorService.save(operator);
			response.getWriter().print("success");
		} else {
			response.getWriter().print("旧密码不正确!");
		}
		response.getWriter().close();
		return null;
	}
	
	public List<Model> getModels() {
		return models;
	}
	
	public void setModels(List<Model> models) {
		this.models = models;
	}
	
	public List<Posts> getPostss() {
		return postss;
	}
	
	public void setPostss(List<Posts> postss) {
		this.postss = postss;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
