package com.hightern.editor.util;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.editor.model.Kindfile;
import com.hightern.editor.service.KindfileService;

@Service("kindfileInfo")
public class KindfileInfo {

	@Resource(name="kindfileService")
	private KindfileService kindfileService;
	
	public static KindfileInfo getFromApplicationContext(ApplicationContext ctx) {
		return  (KindfileInfo) ctx.getBean("kindfileInfo");
	}
	
	
	public void save(Kindfile kindfile){
		kindfileService.save(kindfile);
	}	
	
}
