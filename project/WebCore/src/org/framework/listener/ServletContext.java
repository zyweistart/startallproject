package org.framework.listener;

import javax.servlet.ServletContextEvent;

import org.framework.config.ConfigParameter;
import org.zyweistartframework.context.IServletContext;

public class ServletContext implements IServletContext {

	private TimerTasks task = new TimerTasks();
	
	@Override
	public void initialized(ServletContextEvent servletContextEvent) {
		CacheContext.SERVLETCONTEXTREALPATH=servletContextEvent.getServletContext().getRealPath("/");
		if(ConfigParameter.INITSTATUS){
			//TODO:在此加入系统启动需要初始化的代码
		}
		task.run();
	}

	@Override
	public void destroyed(ServletContextEvent servletContextEvent) {
		if(task!=null){
			task.destroy();
			task=null;
		}
	}

}