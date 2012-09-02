package com.hightern.website.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.website.model.Hyperlink;
import com.hightern.website.model.Information;
import com.hightern.website.service.HyperlinkService;
import com.hightern.website.service.InformationService;


@Service("webSitePageService")
public class WebSitePageService {
	
	@Resource(name = "informationService")
	private InformationService informationService;
	
	@Resource(name = "hyperlinkService")
	private HyperlinkService hyperlinkService;
	
	public static WebSitePageService getFromApplicationContext(ApplicationContext ctx) {
		return (WebSitePageService) ctx.getBean("webSitePageService");
	}
	
	public List<Information> findPhotos(){
		Information info=new Information();
		//发布状态
		info.setReleases(2);
//		info.setMenuId(menuId);
		info.setPhoto(2);
		info.setPageSize(5);
		return informationService.findInformationByMenuId(info);
	}
	/**
	 * 单条记录
	 */
	public Information findInformationBySingleMenuId(Long menuId,Boolean flag){
		Information info=new Information();
		//发布状态
		info.setReleases(2);
		info.setMenuId(menuId);
		info.setPageSize(1);
		if(flag){
			info.setTop(2);
		}
		List<Information> infos=informationService.findInformationByMenuId(info);
		if(infos.isEmpty()){
			return null;
		}
		return infos.get(0);
	}
	/**
	 * 首页信息
	 */
	public List<Information> findInformationByMenuId(Long menuId){
		Information info=new Information();
		//发布状态
		info.setReleases(2);
		info.setMenuId(menuId);
		info.setPageSize(5);
		return informationService.findInformationByMenuId(info);
	}
	
	public List<Hyperlink> getHyperlinks(){
		Hyperlink hl=new Hyperlink();
		hl.setCategory(2);
		return hyperlinkService.getCollection("select o from hyperlink o",hl,false);
	}
	
}