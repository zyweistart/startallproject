package org.zyweistartframework.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zyweistartframework.controller.FilterHostConfig;
import org.zyweistartframework.controller.URLDispatcher;

public final class FilterDispatcher implements Filter {

	private FilterHostConfig filterHostConfig;
	
	public void init(FilterConfig fConfig) throws ServletException {
		filterHostConfig=new FilterHostConfig(fConfig);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)throws IOException,ServletException{
		final HttpServletRequest request=(HttpServletRequest)req;
		final HttpServletResponse response=(HttpServletResponse)res;
		final URLDispatcher dispatch=new URLDispatcher(request,response,filterHostConfig);
		String servletPath=request.getServletPath();
		if(servletPath.indexOf(".")!=-1){
			servletPath=servletPath.substring(0,servletPath.indexOf("."));
		}
		String[] addressPath=servletPath.substring(1).split("/");
		if(addressPath.length==1){
			dispatch.startAnalysis(addressPath[0],chain);
		}else if(addressPath.length==2){
			dispatch.startAnalysis(addressPath[0],addressPath[1],chain);
		}else{
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		filterHostConfig=null;
	}

}