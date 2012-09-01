package org.zyweistartframework.controller.interceptor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.controller.IInterceptor;
import org.zyweistartframework.controller.SessionMap;


/**
 * 表单重复提交拦截器
 */
public final class ToKenInterceptor implements IInterceptor {

	private final static Log log = LogFactory.getLog(ToKenInterceptor.class);
	/**
	 * 唯一表单识别码
	 */
	public final static String TOKENFORMNAME="______START_TOKENINTERCEPTOR_HIDDEN_END______";
	
	@Override
	public void intercept(ControllerInvocation invocation){
		try{
			SessionMap<String, Object> session=new SessionMap<String, Object>(invocation.getRequest());
			Map<String,Object> valMaps=invocation.getBundle().get(TOKENFORMNAME);
			if(valMaps!=null){
				if(valMaps.containsValue(session.get(TOKENFORMNAME))){
					//如果添加了防表单重复提交标签，则判断是否为第一次提交，如果是则从Session中删除，表明已经提交
					session.remove(TOKENFORMNAME);
				}else{
					log.error("表单重复提交！");
					return;
				}
			}
		}finally{
			invocation.doInterceptor();
		}
	}

}