package org.zyweistartframework.controller.result;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.controller.IActionResult;
import org.zyweistartframework.utils.StackTraceInfo;

public final class Print implements IActionResult {
	
	private final static Log log=LogFactory.getLog(Print.class);

	private String message;
	
	private String contentType="text/html;charset=utf-8";
	
	public Print(String message){
		this.message=message;
	}
	
	public Print(String message,String contentType){
		this.message=message;
		this.contentType=contentType;
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		HttpServletResponse response=invocation.getResponse();
		response.setContentType(contentType);
		PrintWriter pw=null;
		try {
			pw=response.getWriter();
			pw.print(message);
			pw.flush();
		} catch (IOException e) {
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}

}