package com.hightern.kernel.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitialWebApplicationContextServlet extends GenericServlet {
	
	private static final long serialVersionUID = 1L;
	private static WebApplicationContext wac = null;
	
	public static WebApplicationContext getWebApplicationContext() {
		return wac;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (wac == null) {
			wac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		}
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {}
}
