package org.zyweistartframework.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zyweistartframework.context.ContextInjection;
import org.zyweistartframework.controller.IInterceptor.ControllerInvocation.InvokeAction;


public abstract class ActionSupport{

	protected IFilterHostConfig hostConfig;
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected ContextInjection contextInjection;
	
	private RequestMap requestMap;
	
	private SessionMap<String, Object> sessionMap;
	
	private ApplicationMap applicationMap;
	
	public void setHostConfig(IFilterHostConfig hostConfig) {
		this.hostConfig=hostConfig;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request=request;
	}

	public void setResponse(HttpServletResponse rseponse) {
		this.response=rseponse;
	}

	public void setContextInjection(ContextInjection contextInjection) {
		this.contextInjection = contextInjection;
	}
	/**
	 * 请求对象域
	 */
	protected RequestMap RequestMap(){
		if(requestMap==null){
			requestMap=new RequestMap(request);
		}
		return requestMap;
	}
	/**
	 * Session会话对象域
	 */
	protected SessionMap<String,Object> SessionMap(){
		if(sessionMap==null){
			sessionMap=new SessionMap<String, Object>(request);
		}
		return sessionMap;
	}
	/**
	 * 应用程序域
	 */
	protected ApplicationMap ApplicationMap(){
		if(applicationMap==null){
			applicationMap=new ApplicationMap(request.getServletContext());
		}
		return applicationMap;
	}
	/**
	 * 主体ACTION方法拦截器
	 */
	public abstract void intercept(InvokeAction invokeAction);
	/**
	 * 默认执行的方法
	 */
	public IActionResult execute(){return null;}
	
}