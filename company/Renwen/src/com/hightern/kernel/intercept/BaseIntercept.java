package com.hightern.kernel.intercept;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class BaseIntercept extends AbstractInterceptor {
	
	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 默认的安全拦截器
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		System.out.println(ServletActionContext.getRequest().getRequestURI());
		
		return invocation.invoke();
//		Map session = invocation.getInvocationContext().getSession();
//		Operator operator = (Operator) session.get("currentUser");
//		if (ServletActionContext.getRequest().getRequestURI().indexOf("_") < 1) {
//			if (invocation.getAction() instanceof RandomAction) {
//				return invocation.invoke();
//			} else if (invocation.getAction() instanceof DispatchAction) {
//				return invocation.invoke();
//			}  else {
//				if (operator != null) {
//					return invocation.invoke();
//				} else {
//					return Action.INPUT;
//				}
//			}
//		} else {
//			String url = ServletActionContext.getRequest().getRequestURI().substring(ServletActionContext.getRequest().getRequestURI().indexOf("_"),
//					ServletActionContext.getRequest().getRequestURI().length());
//			if ("_list.htm".equals(url)) {
//				return invocation.invoke();
//			} else if ("_detail.htm".equals(url)) {
//				return invocation.invoke();
//			} else if ("_getNewsByTypeId.htm".equals(url)) {
//				return invocation.invoke();
//			} else if ("_bulletinList.htm".equals(url)) {
//				return invocation.invoke();
//			} else if ("_newslist.htm".equals(url)) {
//				return invocation.invoke();
//			} else if ("_getNewsByTypeId.htm".equals(url)) {
//				return invocation.invoke();
//			} else if ("_list_home.htm".equals(url)) {
//				return invocation.invoke();
//			}else if (invocation.getAction() instanceof RandomAction) {
//				return invocation.invoke();
//			} else if (invocation.getAction() instanceof DispatchAction) {
//				return invocation.invoke();
//			} else {
//				if (operator != null) {
//					return invocation.invoke();
//				} else {
//					return Action.INPUT;
//				}
//			}
//		}
	}
	
}
