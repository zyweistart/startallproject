/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.test.dao;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.test.model.Cs;

public interface CsDao extends BaseDao<Cs, Long> {
	 public void updateStort();
}
