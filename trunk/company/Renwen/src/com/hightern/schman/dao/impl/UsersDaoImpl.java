/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.schman.dao.UsersDao;
import com.hightern.schman.model.Users;

@Repository("usersDao")
public class UsersDaoImpl extends BaseDaoImpl<Users, Long> implements UsersDao {

	public UsersDaoImpl() {
		super(Users.class, "users");
	}

	/***
	 * 判断用户名是否存在
	 * true 存在  false 不存在
	 */
	@SuppressWarnings("unchecked")
	public boolean judgeName(String userName) {
		List<Users> users = this
				.getEntityManager()
				.createQuery(
						"select o from users o where o.userName = '" + userName
								+ "'").getResultList();
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Users> hquserMag(String userName,String password){
		return this.getEntityManager().createQuery("select o from users o where o.userName=? and o.password=?").setParameter(1, userName).setParameter(2, password).getResultList();
	}
	
	
	
}
