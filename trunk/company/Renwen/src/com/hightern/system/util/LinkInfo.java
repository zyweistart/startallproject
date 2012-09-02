package com.hightern.system.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hightern.system.model.Link;
import com.hightern.system.service.LinkService;

@Service("linkInfo")
public class LinkInfo {
	
	@Resource(name = "linkService")
	private LinkService linkService;
	
	public static LinkInfo getLinkFromApplicationContext(ApplicationContext ctx) {
		return (LinkInfo) ctx.getBean("linkInfo");
	}
	
	public List<Link> getHomeLink() {
		final List<Link> list = linkService.getHomeLink();
		return list;
	}
}
