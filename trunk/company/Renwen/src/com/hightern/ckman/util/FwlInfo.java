package com.hightern.ckman.util;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.ckman.model.Fwl;
import com.hightern.ckman.service.FwlService;

@Service("fwlInfo")
public class FwlInfo {

	@Resource
	private FwlService fwlService;
	
	public static FwlInfo getFromApplicationContext(ApplicationContext ctx) {
		return  (FwlInfo) ctx.getBean("fwlInfo"); 
	}
	
	public String getls(){
		String nl="";
		Fwl fwl=fwlService.findById(new Long(2));
		if(fwl!=null){
			nl=fwl.getLs().toString();
		}
		return nl;
	}
	
	
	
}
