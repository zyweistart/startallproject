package com.hightern.consult.util; 

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.consult.model.Integration;
import com.hightern.consult.service.IntegrationService;


@Service("integrationInfo")
public class IntegrationInfo {
  
	@Resource(name="integrationService")
	private IntegrationService integrationService;
	
	
	public static IntegrationInfo getFromApplicationContext(ApplicationContext ctx) {
		return (IntegrationInfo) ctx.getBean("integrationInfo");
	}
	
	//关于创意内容
	 public Integration getGsnr(){
		 return integrationService.getGsnr();
	 }
	
	//核心产品（首页）
	 public List<Integration> getkxcp(){
		 return integrationService.getInformation(new Long(15), new Long(3), 2);
	 }
	 
	//解决方案（首页）
	 public List<Integration> getjjfa(){
		 return integrationService.getInform(new Long(4), new Long(99), 2);
	 }
	 
	 

	//首页新闻图片
	 public List<Integration> getnews()
	 {
		 return integrationService.getnews();
	 }
	 
	 //通知公告
	 public List<Integration> getgg(){
		 return integrationService.getgg(10);
	 }
	
}

