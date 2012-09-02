/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.schman.model.Users;

public interface UsersService extends BaseService<Users, Long> {
	/***
	 *  判断用户名是否存在
	 * @param userName 用户名
	 * @return true 存在  false 不存在
	 */
	 public boolean judgeName(String userName);
	 
	 /***
	  *获取信息 
	  * @param userName
	  * @param password
	  * @return
	  */
	 public List<Users> hquserMag(String userName,String password);
}