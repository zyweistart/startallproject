package org.zyweistartframework.context;

import javax.servlet.ServletContextEvent;

/**
 * Servlet容器
 * @author Start
 */
public interface IServletContext {
	
	void initialized(ServletContextEvent servletContextEvent);
	
	void destroyed(ServletContextEvent servletContextEvent);
	
}
