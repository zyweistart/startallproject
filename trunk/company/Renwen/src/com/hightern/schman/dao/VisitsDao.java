/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.schman.dao;

import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.schman.model.Visits;

public interface VisitsDao extends BaseDao<Visits, Long> {
	public String  hqvisitsMax(Integer type,Long userId);
	public List<Visits> hqvisitsMag(Integer type,Long userId);
	public String  hqsizeMax(Integer type,Long userId);
}
