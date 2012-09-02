/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.consult.dao;

import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.consult.model.Templatetype;

public interface TemplatetypeDao extends BaseDao<Templatetype, Long> {
	  public List<Templatetype> obtainMag(Integer status);
	
}
