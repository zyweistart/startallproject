/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hightern.kernel.service.BaseService;
import com.hightern.website.model.Information;

public interface InformationService extends BaseService<Information, Long> {

	boolean findInformationByMenu(Long menuId);
	
	List<Information> findInformationByMenuId(Information info);
	
	List<Information> findInformationByPage(HttpServletRequest request,Information info);
	
}