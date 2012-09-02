package com.hightern.consult.util;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.consult.model.Cpymag;
import com.hightern.consult.service.CpymagService;

@Service("cpymagInfo")
public  class CpymagInfo  {
	@Resource(name="cpymagService")
	private CpymagService cpymagService;
	
	public static  CpymagInfo getFromApplicationContext(ApplicationContext ctx) {
		return (CpymagInfo) ctx.getBean("cpymagInfo");
	}
	
	
	 public Cpymag hqContact(){
		 return cpymagService.hqContact();
	 }
	
}
