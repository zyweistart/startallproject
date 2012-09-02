/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.schman.dao;

import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.schman.model.Users;

public interface UsersDao extends BaseDao<Users, Long> {
	public boolean judgeName(String userName);
	
	public List<Users> hquserMag(String userName,String password);
}
