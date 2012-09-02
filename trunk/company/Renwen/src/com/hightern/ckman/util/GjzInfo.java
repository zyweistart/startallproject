package com.hightern.ckman.util;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.ckman.model.Gjz;
import com.hightern.ckman.service.GjzService;

@Service("gjzInfo")
public class GjzInfo {

	@Resource
	private GjzService gjzService;
	
	public static GjzInfo getFromApplicationContext(ApplicationContext ctx){
		return (GjzInfo)  ctx.getBean("gjzInfo");
	}
	
	
	public Gjz getgjz(){
		Gjz gjz=gjzService.findById(new Long(1));
		if(gjz==null){
			gjz=new Gjz();
		}
		return gjz;
	}
	
	
}
