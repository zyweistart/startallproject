package com.hightern.system.action;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.listener.OnLineOperatorListener;
import com.hightern.kernel.util.DateHelper;
import com.hightern.kernel.util.MD5;
import com.hightern.system.model.Logs;
import com.hightern.system.model.Model;
import com.hightern.system.model.Operator;
import com.hightern.system.model.Posts;
import com.hightern.system.model.Role;
import com.hightern.system.service.LogsService;
import com.hightern.system.service.ModelService;
import com.hightern.system.service.OperatorService;
import com.hightern.system.service.PostsService;
import com.hightern.system.service.RoleService;
import com.opensymphony.xwork2.Action;

/**
 * 安全控制器
 * 
 * @author Stone
 */
@Scope("prototype")
@Controller("dispatchAction")
public class DispatchAction extends BaseAction<Operator> {
	
	private static final long serialVersionUID = 1L;
	private static final String FORWARD_SHOW_LEFT = "/common/tree.jsp";
	private static final String JPQL_CHECKLOGIN_OPERATOR = "select o from operator o ";
	private static final String FORWARD_CONTROLLER_PAGE = "/common/main.jsp";
	@Resource(name = "operatorService")
	private OperatorService operatorService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "logsService")
	private LogsService logsService;
	@Resource(name = "modelService")
	private ModelService modelService;
	@Resource(name = "postsService")
	private PostsService postsService;
	private List<Model> models;
	private Operator operator;
	private String username;
	private String password;
	private String randcode;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String execute() throws Exception {
		if (isNullOrEmptyString(this.getUsername()) || isNullOrEmptyString(this.getPassword())) {
			this.setMessage("faile");
			return Action.INPUT;
		} else {
			final String rand = (String) session.get("rand_code");
			if ((rand + "").equals(this.getRandcode())) {
			
			
			operator = new Operator();
			operator.setLoginName(this.getUsername());
			operator.setPassword(MD5.calcMD5(this.getPassword()));
			final Map map = operatorService.buildReflectJpql(DispatchAction.JPQL_CHECKLOGIN_OPERATOR, operator);
			final List<Operator> operators = operatorService.queryForObject(this.getJpql(map), this.getParams(map));
			if (operators != null && operators.size() > 0) {
				for (final Operator user : operators) {
					session.put("currentUser", user);
					final OnLineOperatorListener onLineOperatorListener = new OnLineOperatorListener(user);
					request.getSession().setAttribute("onLineOperatorListener", onLineOperatorListener);
					final Logs logs = new Logs();
					logs.setLoginDate(DateHelper.getFullDate());
					logs.setLoginName(user.getTrueName());
					logs.setLoginIp(request.getRemoteAddr());
					logsService.save(logs);
					break;
				}
				
				this.setParameters(DispatchAction.FORWARD_CONTROLLER_PAGE);
				return Action.SUCCESS;
			} else {
				this.setMessage("faile");
				return Action.INPUT;
			}
		} else {
			this.setMessage("faile");
			return Action.INPUT;
			
		}	
		}
	}
	
	/**
	 * 显示左边导航
	 * 
	 * @return Success
	 * @throws SystemExceptions
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String showLeft() throws SystemExceptions {
		operator = (Operator) session.get("currentUser");
		final Set set = new TreeSet();
		String JPQL = "";
		if (null != operator) {
			if (!isNullOrEmptyString(operator.getRoles())) {
				final String[] codes = operator.getRoles().split(",");
				for (int i = 0; i < codes.length; i++) {
					if (codes[i] != null && !"".equals(codes[i])) {
						final List<Role> roles = roleService.queryForObject("select o from role o where o.roleCode='" + codes[i] + "'", null);
						if (roles != null && roles.size() > 0) {
							for (final Role role : roles) {
								if (role.getModels() != null) {
									final String[] roleModel = role.getModels().split(",");
									for (final String element : roleModel) {
										set.add(element);
									}
								}
							}
						}
					}
				}
			}
			
			if (!isNullOrEmptyString(operator.getPostCode())) {
				final List<Posts> posts = postsService.queryForObject("select o from posts o where o.postCode ='" + operator.getPostCode() + "'",
						null);
				if (posts != null && posts.size() > 0) {
					for (final Posts post : posts) {
						if (post.getModels() != null && post.getModels().length() > 0) {
							final String[] postModel = post.getModels().split(",");
							for (final String element : postModel) {
								set.add(element);
							}
						}
					}
				}
			}
			
			if (operator.getModels() != null) {
				final String[] model = operator.getModels().split(",");
				for (final String element : model) {
					set.add(element);
				}
			}
			
			final StringBuffer sb = new StringBuffer();
			sb.append("select o from model o where 1=1 and (");
			for (final Object o : set) {
				if (o.toString().length() > 0) {
					sb.append("o.id=" + o + " or ");
				}
			}
			if (sb.toString().contains("or")) {
				JPQL = sb.toString().substring(0, sb.toString().lastIndexOf("or"));
				JPQL = JPQL + ")";
				models = modelService.queryForObject(JPQL, null);
			} else {
				models = null;
			}
			this.setParameters(DispatchAction.FORWARD_SHOW_LEFT);
		}
		return Action.SUCCESS;
	}
	
	public List<Model> getModels() {
		return models;
	}
	
	public void setModels(List<Model> models) {
		this.models = models;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getRandcode() {
		return randcode;
	}

	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}
	
}
