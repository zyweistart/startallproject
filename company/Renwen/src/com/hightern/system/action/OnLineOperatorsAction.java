package com.hightern.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.system.model.Operator;
import com.opensymphony.xwork2.Action;
import com.sun.net.httpserver.Authenticator.Success;

@Scope("prototype")
@Controller("onLineOperatorsAction")
public class OnLineOperatorsAction extends BaseAction<Operator> {
	
	private static final long serialVersionUID = 5277545856673120429L;
	private static String ON_LINE_OPERATORS = "operatorList";
	private static final String FORWARD_SHOW_ONLINEUSER = "/system/show_onlineUser.jsp";
	
	@SuppressWarnings("rawtypes")
	private List onLineOperators;
	
	/**
	 * 移出在线人员(退出时调用此方法)
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 */
	@SuppressWarnings("rawtypes")
	public String removeOnLineOperator() throws SystemExceptions {
		final Map map = (Map) request.getSession().getServletContext().getAttribute(OnLineOperatorsAction.ON_LINE_OPERATORS);
		map.remove(this.getCurrentUser().getId());
		request.getSession().getServletContext().setAttribute(OnLineOperatorsAction.ON_LINE_OPERATORS, map);
		request.getSession().invalidate();
		return null;
	}
	
	/**
	 * 注销系统时调用此方法
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	@SuppressWarnings("rawtypes")
	public String removeOperator() throws SystemExceptions {
		final Map map = (Map) request.getSession().getServletContext().getAttribute(OnLineOperatorsAction.ON_LINE_OPERATORS);
		map.remove(this.getCurrentUser().getId());
		request.getSession().getServletContext().setAttribute(OnLineOperatorsAction.ON_LINE_OPERATORS, map);
		request.getSession().invalidate();
		this.setParameters(request.getServletPath());
		return Action.SUCCESS;
	}
	
	/**
	 * 取得在线人员列表
	 * 
	 * @return {@link Success}
	 * @throws SystemExceptions
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getOnlineUser() throws SystemExceptions {
		final Map map = (Map) request.getSession().getServletContext().getAttribute(OnLineOperatorsAction.ON_LINE_OPERATORS);
		if (isNullOrEmptyString(map)) { throw new SystemExceptions("在线人员为空!"); }
		if (onLineOperators == null) {
			onLineOperators = new ArrayList();
		}
		for (final Iterator it = map.entrySet().iterator(); it.hasNext();) {
			final String value = it.next().toString();
			if (value != null) {
				onLineOperators.add(value.substring(2, value.length()));
			}
		}
		this.setParameters(OnLineOperatorsAction.FORWARD_SHOW_ONLINEUSER);
		return Action.SUCCESS;
	}
	
	/**
	 * 得到在线人员
	 * 
	 * @return <code>null</code>
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	@SuppressWarnings({"rawtypes" })
	public String getNumber() throws SystemExceptions, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		final HttpSession session = request.getSession(false);
		if (session != null) {
			final Map hashMap = (Map) session.getServletContext().getAttribute(OnLineOperatorsAction.ON_LINE_OPERATORS);
			int olNum = 0;
			if (hashMap != null) {
				olNum = hashMap.size();
			}
			response.getWriter().write(String.valueOf(olNum));
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public List getOnLineOperators() {
		return onLineOperators;
	}
	
	@SuppressWarnings("rawtypes")
	public void setOnLineOperators(List onLineOperators) {
		this.onLineOperators = onLineOperators;
	}
	
}
