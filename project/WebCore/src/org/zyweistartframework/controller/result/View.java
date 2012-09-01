package org.zyweistartframework.controller.result;

import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.controller.IActionResult;
import org.zyweistartframework.utils.StackTraceInfo;


/**
 * 转向
 */
public class View implements IActionResult {
	
	private final static Log log=LogFactory.getLog(View.class);
	
	private String dispatcherPage;
	
	public View(String dispatcherPage){
		this.dispatcherPage=dispatcherPage;
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		try{
			HttpServletRequest request=invocation.getRequest();
			HttpServletResponse response=invocation.getResponse();
			//设置Request作用域的值
			for(Method method:invocation.getTargetEntity().getClass().getMethods()){
				String methodName=method.getName();
				if(methodName.startsWith(Constants.GET)||methodName.startsWith(Constants.IS)){
					String fieldName=null;
					if(methodName.startsWith(Constants.IS)){
						fieldName=methodName.substring(2,3).toLowerCase()+methodName.substring(3);
					}else  if(methodName.startsWith(Constants.GET)){
						fieldName=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
					}
					if(fieldName!=null){
						//只添加不存在的键值
						if(request.getAttribute(fieldName)==null){
							request.setAttribute(fieldName,method.invoke(invocation.getTargetEntity()));
						}
					}
				}
			}
			RequestDispatcher requestDispatcher=request.getRequestDispatcher(getDispatcherPage());
			requestDispatcher.forward(request,response);
		}catch(Exception e){
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
		}
	}

	public String getDispatcherPage() {
		return dispatcherPage;
	}
	
}
