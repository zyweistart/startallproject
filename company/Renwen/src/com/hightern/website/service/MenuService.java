/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.website.model.Menu;

public interface MenuService extends BaseService<Menu, Long> {

	List<Menu> findMenuByParentId(Long parentId);
	
	List<Menu> findMenuByLevel(Integer level);
	
}