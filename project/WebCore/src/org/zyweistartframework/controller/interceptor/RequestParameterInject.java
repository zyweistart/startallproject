package org.zyweistartframework.controller.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.controller.IInterceptor.ControllerInvocation;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.utils.StackTraceInfo;


/**
 * 提交请求时，给Action类注入参数的辅助类
 * @author Start
 */
public final class RequestParameterInject {
	
	private final static Log log = LogFactory.getLog(RequestParameterInject.class);
	/**
	 * 初始化注入
	 */
	public static void inject(ControllerInvocation invocation){
		for(String parameter:invocation.getBundle().keySet()){
			Map<String,Object> keyVals=invocation.getBundle().get(parameter);
			for(String type:keyVals.keySet()){
				Object val=keyVals.get(type);
				if(Constants.PARAMETER_TEXT.equals(type)){
					injectParameter(parameter,val,invocation.getMethods(),invocation.getControllerEntity(),invocation.getCaches());
				}else if(Constants.PARAMETER_CHECKBOX.equals(type)){
					Object[] vals=(Object[]) val;
					StringBuilder strVals=new StringBuilder();
					for(Object v:vals){
						strVals.append(v+Constants.COMMA);
					}
					strVals.deleteCharAt(strVals.length()-1);
					injectParameter(parameter,strVals.toString(),invocation.getMethods(),invocation.getControllerEntity(),invocation.getCaches());
				}else if(Constants.PARAMETER_FILE.equals(type)){
					for(Method method:invocation.getMethods()){
						if(method.getName().equals(Constants.SETl+parameter.substring(0,1).toUpperCase()+parameter.substring(1))){
				       		try {
								method.invoke(invocation.getControllerEntity(),val);
							} catch (IllegalArgumentException e) {
								log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
							} catch (IllegalAccessException e) {
								log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
							} catch (InvocationTargetException e) {
								log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
							}
				       	}
					}
				}else{
					String message=type+"注入的参数类型有误！";
					log.error(StackTraceInfo.getTraceInfo()+message);
					throw new IllegalArgumentException(message);
				}
			}
		}
	}
	/**
	 * Request参数注入
	 * @param parameter
	 * 参数名称
	 * @param value
	 * 参数所对应的值
	 * @param methods
	 * 对应类的所有方法
	 * @param entity
	 * 要注入的实体类对象
	 * @param caches
	 * 参数缓存
	 */
	static void injectParameter(String parameter,Object value,Method[] methods,Object entity,Map<Class<?>,Object> caches){
		Object injectObj=entity;
		String[] params=parameter.split("\\.");
		for(int i=0;i<params.length;i++){
			for (Method method : methods){
				String methodName=method.getName();
				if (methodName.startsWith(Constants.SETl)) {
					String fieldName=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
					if (fieldName.equals(params[i].trim())){
						//只注入代参数且为一个的
						if(method.getParameterTypes().length==1){
							//如果是最后一个参数则注入实际的值
							if(params.length-1==i){
								EntityFactory.getInstance().injectParameter(injectObj,method, method.getParameterTypes()[0].getSimpleName(),value);
							}else{
								//如果不是最后一个参数则表明是一个对象则创建实例
								Class<?> prototype=method.getParameterTypes()[0];
								Object tmpObj=caches.get(prototype);
								if(tmpObj==null){
									//加入缓存
									try {
										tmpObj=prototype.newInstance();
									} catch (InstantiationException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									} catch (IllegalAccessException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									}
									caches.put(prototype, tmpObj);
								}
								if(i==0){
									try {
										method.invoke(entity,tmpObj);
									} catch (IllegalArgumentException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									} catch (IllegalAccessException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									} catch (InvocationTargetException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									}
								}else{
									try {
										method.invoke(injectObj,tmpObj);
									} catch (IllegalArgumentException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									} catch (IllegalAccessException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									} catch (InvocationTargetException e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									}
								}
								injectObj=tmpObj;
								methods=prototype.getMethods();
								tmpObj=null;
							}
							break;
						}
					}
				}
			}
		}
	}
}