/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.system.dao.LinkDao;
import com.hightern.system.model.Link;

@Repository("linkDao")
public class LinkDaoImpl extends BaseDaoImpl<Link, Long> implements LinkDao {

	public LinkDaoImpl() {
		super(Link.class, "link");
	}

	@SuppressWarnings("unchecked")
	public List<Link> getHomeLink() {
		return this.getEntityManager().createQuery(
				"select o from link o where o.isShow = 99 order by o.id desc").getResultList();
	}
}
