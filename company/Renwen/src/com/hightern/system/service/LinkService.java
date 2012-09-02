/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.system.model.Link;

public interface LinkService extends BaseService<Link, Long> {
	
	public List<Link> getHomeLink();
}