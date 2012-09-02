/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.schman.model.Visits;

public interface VisitsService extends BaseService<Visits, Long> {
	
	/***
	 * 获取访问量
	 * @param type
	 * @param userId
	 * @return
	 */
	public String  hqvisitsMax(Integer type,Long userId);
	
	/***
	 * 获取信息
	 * @param type
	 * @param userId
	 * @return
	 */
	public List<Visits> hqvisitsMag(Integer type,Long userId);
	
	/***
	 * 获取访问次数
	 * @param type
	 * @param userId
	 * @return
	 */
	public String  hqsizeMax(Integer type,Long userId);
}