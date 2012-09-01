package org.zyweistartframework.controller.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zyweistartframework.config.Constants;
import org.zyweistartframework.controller.IInterceptor;
/**
 * 非multipart/form-data提交方式拦截,各参数设置
 */
public final class ParametersInterceptor implements IInterceptor {

	
	
	@Override
	public void intercept(ControllerInvocation invocation){
		HttpServletRequest request=invocation.getRequest();
		try{
			if(!Constants.MULTIPARTFORMDATA.equals(request.getContentType())){
				Enumeration<String> enumerations = request.getParameterNames();
				while (enumerations.hasMoreElements()){
					String parameterName = enumerations.nextElement();
					String[] values=request.getParameterValues(parameterName);
					//参数存储
					Map<String,Object> param=new HashMap<String,Object>();
					if(values.length==1){
						//如果参数只为一个则一般为文本框，单选框等提交
						param.put(Constants.PARAMETER_TEXT, values[0]);
					}else{
						//如果参数大于一个则一般为复选框提交
						param.put(Constants.PARAMETER_CHECKBOX,values);
					}
					invocation.getBundle().put(parameterName,param);	
				}
				RequestParameterInject.inject(invocation);
			}
		}finally{
			invocation.doInterceptor();
		}
	}

}