/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.system.model.Model;

public interface ModelService extends BaseService<Model, Long> {
	
	/**
	 * 取子菜单
	 * 
	 * @return {@link List}
	 */
	public List<Model> getChildrenModels(Long id);
}