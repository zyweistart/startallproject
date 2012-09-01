package org.zyweistartframework.controller.result;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.utils.StackTraceInfo;


/**
 * 重定向
 */
public final class Redirect extends View {
	
	private final static Log log=LogFactory.getLog(Redirect.class);

	public Redirect(String dispatcherPage) {
		super(dispatcherPage);
	}

	@Override
	public void doExecute(ActionResultInvocation invocation) {
		HttpServletResponse response=invocation.getResponse();
		try {
			response.sendRedirect(getDispatcherPage());
		} catch (IOException e) {
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
		}
	}

}
