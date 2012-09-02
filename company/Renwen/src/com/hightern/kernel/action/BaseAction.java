package com.hightern.kernel.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.hightern.kernel.model.BaseModel;

import com.hightern.schman.model.Users;
import com.hightern.system.model.Operator;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction<T extends BaseModel> extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {
	
	private static final long serialVersionUID = -1982489057575902968L;
	public static final String FORWARD_SUCCESS = "/common/success.jsp";
	public static final String FORWARD_OP_SUCCESS = "/common/opSuccess.jsp";
	public static final String FORWARD_SHOW_MESSAGE = "/common/message.jsp";
	// 建立session会话对象
	@SuppressWarnings({ "rawtypes" })
	protected Map session;
	// 建立requset请求对象
	protected HttpServletRequest request;
	// 建立response响应对象
	protected HttpServletResponse response;
	
	// 取得当前操作人员
	@SuppressWarnings("unused")
	private Operator currentUser;
	// 输入页面的地址
	private String inputPage;
	// 转向用到的地址
	private String url;
	// 提示消息
	private String message;
	// 主键编号
	private Long id;
	// 选择的编号
	private String selectedIds;
	// 数据范围
	private String scope;
	
	// 取得当前操作人员
	@SuppressWarnings("unused")
	private Users stuUser;
	
	// 成功页面的地址
	private String successPage = BaseAction.FORWARD_SUCCESS;
	// 成功后页面的地址（用于弹出的页面）
	private String successPageOp = BaseAction.FORWARD_OP_SUCCESS;
	
	@Override
	public String execute() throws Exception {
		request.setAttribute("Lang", request.getLocale().toString());
		return super.execute();
	}
	
	/**
	 * 取得jpql语句
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({  "rawtypes" })
	public String getJpql(Map map) {
		if (null == map) { return null; }
		String jpql = "";
		for (final Iterator it = map.keySet().iterator(); it.hasNext();) {
			jpql = it.next().toString();
		}
		return jpql;
	}
	
	/**
	 * 取得jpql语句中需要的参数值
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({  "rawtypes" })
	public Object[] getParams(Map map) {
		if (null == map) { return null; }
		List list = new ArrayList();
		for (final Iterator it = map.keySet().iterator(); it.hasNext();) {
			final Object key = it.next();
			if (null == map.get(key)) { return null; }
			list = (ArrayList) map.get(key);
		}
		return list.toArray();
	}
	
	/**
	 * 将客户端传递过来的id字符串处理成为List
	 * 
	 * @param code
	 * @return List<Long>
	 */
	protected List<Long> idsStringToList(String code) {
		final List<Long> ids = new ArrayList<Long>();
		final String[] idsArray = code.split(",");
		for (final String element : idsArray) {
			ids.add(Long.parseLong(element.trim()));
		}
		return ids;
	}
	
	
	/**
	 * 将客户端传递过来的id字符串处理成为List
	 * 
	 * @param code
	 * @return List<String>
	 */
	protected List<String> iStringToList(String code) {
		final List<String> ids = new ArrayList<String>();
		final String[] idsArray = code.split(",");
		for (final String element : idsArray) {
			ids.add(element.trim());
		}
		return ids;
	}
	
	
	/**
	 * 将客户端传递过来的编号字符串处理成为List
	 * 
	 * @param code
	 * @return List<String>
	 */
	protected List<String> codeStringToList(String code) {
		final List<String> ids = new ArrayList<String>();
		final String[] idsArray = code.split(",");
		for (final String element : idsArray) {
			ids.add(element.trim());
		}
		return ids;
	}
	
	/**
	 * 判断对象是否为空
	 * 
	 * @param object
	 * @return boolean
	 */
	public boolean isNullOrEmptyString(Object object) {
		if (object == null) { return true; }
		if (object instanceof String) {
			final String str = (String) object;
			if (str.length() == 0) { return true; }
		}
		return false;
	}
	
	/**
	 * 设置转向的地址
	 * 
	 * @param successPage
	 */
	protected void setParameters(String successPage) {
		this.setSuccessPage(successPage);
	}
	
	/**
	 * 设置转向的地址
	 * 
	 * @param message
	 * @param url
	 */
	protected void setParameters(String message, String url) {
		this.setSuccessPage(successPage);
		this.setMessage(message);
		this.setUrl(url);
	}
	
	/**
	 * 无数据权限时所转的页面!
	 * 
	 * @param message
	 * @param url
	 */
	protected void setShowMessage(String message) {
		this.setSuccessPage(BaseAction.FORWARD_SHOW_MESSAGE);
		this.setMessage(message);
	}
	
	/**
	 * 设置转向的地址
	 * 
	 * @param successPage
	 * @param message
	 * @param url
	 */
	protected void setParameters(String successPage, String message, String url) {
		this.setSuccessPage(successPage);
		this.setMessage(message);
		this.setUrl(url);
	}
	
	/**
	 * 设置转向的地址
	 * 
	 * @param message
	 * @param url
	 */
	protected void setParametersOp(String message, String url) {
		this.setSuccessPage(successPageOp);
		this.setMessage(message);
		this.setUrl(url);
	}
	
	public void setServletRequest(HttpServletRequest requeset) {
		this.request = requeset;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	@SuppressWarnings({  "rawtypes" })
	public void setSession(Map session) {
		this.session = session;
	}
	
	public Operator getCurrentUser() {
		return (Operator) session.get("currentUser");
	}
	
	public String getInputPage() {
		return inputPage;
	}
	
	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	public String getSuccessPage() {
		return successPage;
	}
	
	public void setSuccessPage(String successPage) {
		this.successPage = successPage;
	}
	
	public String getSuccessPageOp() {
		return successPageOp;
	}
	
	public void setSuccessPageOp(String successPageOp) {
		this.successPageOp = successPageOp;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	
	public Users getStuUser() {
		return (Users) session.get("stuUser");
	}

	public void setStuUser(Users stuUser) {
		this.stuUser = stuUser;
	}
	
	
}
