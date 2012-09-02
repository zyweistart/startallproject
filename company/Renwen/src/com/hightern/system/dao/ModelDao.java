/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.dao;

import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.system.model.Model;

public interface ModelDao extends BaseDao<Model, Long> {
	/**
	 * 根据ID取子菜单
	 * 
	 * @param id
	 * @return {@link List}
	 */
	public List<Model> getChildren(Long id);
}
