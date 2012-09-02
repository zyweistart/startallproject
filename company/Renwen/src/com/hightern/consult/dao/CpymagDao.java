/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.consult.dao;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.consult.model.Cpymag;

public interface CpymagDao extends BaseDao<Cpymag, Long> {
	 public Cpymag hqContact();
}
