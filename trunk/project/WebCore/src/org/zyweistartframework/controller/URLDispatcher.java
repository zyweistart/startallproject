package org.zyweistartframework.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.GlobalEntityContext;
import org.zyweistartframework.controller.IInterceptor.ControllerInvocation;
import org.zyweistartframework.exception.HibernateException;
import org.zyweistartframework.utils.StackTraceInfo;


/**
 * URL解析触发类
 */
public final class URLDispatcher {
	
	private final static Log log=LogFactory.getLog(URLDispatcher.class);
	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final FilterHostConfig fhostConfig;
	
	public URLDispatcher(HttpServletRequest request,HttpServletResponse response,FilterHostConfig fhostConfig){
		this.request=request;
		this.response=response;
		this.fhostConfig=fhostConfig;
	}
	/**
	 * 解析
	 * @param action
	 * 控制层的Action别名
	 * <pre>
	 * 会传递默认执行的方法
	 * 注：却保在调用该方法的时候把该方法放在最后执行,
	 * 因为控制器不存在则会调用FilterChain的doFilter方法继续执行下一个过滤器
	 * </pre>
	 */
	public void startAnalysis(String controller,FilterChain chain) throws IOException, ServletException{
		startAnalysis(controller,Constants.EXECUTE,chain);
	}
	/**
	 * 解析
	 * @param action
	 * 控制层的Action别名
	 * @param execute
	 * 要在Action类中执行的方法名称
	 * <pre>
	 * 注：却保在调用该方法的时候把该方法放在最后执行,
	 * 因为控制器不存在则会调用FilterChain的doFilter方法继续执行下一个过滤器
	 * </pre>
	 */
	public void startAnalysis(String controller,String execute,FilterChain chain) throws IOException, ServletException{
		final Class<?> action=GlobalEntityContext.getActions().get(controller);
		if(action!=null){
			//请求编码设置防止乱码
			if (request.getCharacterEncoding()==null) {
				request.setCharacterEncoding(ConfigConstant.ENCODING);
		    }
			//拦截器
			ControllerInvocation controllerInvocation=new ControllerInvocation(request,response,fhostConfig,action);
			try{
				//设置执行的方法,方法如果不存在则会引发NullPointerException
				controllerInvocation.setMethod(execute);
				//数据注入
				controllerInvocation.injectdata();
				//拦截器调用
				controllerInvocation.doInterceptor();
			}finally{
				if(controllerInvocation!=null){
					try {
						controllerInvocation.getContextInjection().finalize();
					} catch (Throwable e) {
						String message=Message.getMessage(Message.PM_5015,e.getMessage());
						log.error(StackTraceInfo.getTraceInfo() + message);
						throw new HibernateException(message);
					}
					controllerInvocation=null;
				}
			}
		}else{
			//如果当前控制层不存在则会继续执行下一个过滤器
			chain.doFilter(request,response);
		}
	}
}