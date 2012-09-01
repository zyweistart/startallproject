package org.zyweistartframework.persistence.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

import org.zyweistartframework.config.Constants;

public class ASMCallbackFilter implements CallbackFilter {
	
	@Override
	public int accept(Method method) {
		if(method.getName().startsWith(Constants.SETl)){
			return Constants.TRUE;
		}
		return Constants.FALSE;
	}

}
