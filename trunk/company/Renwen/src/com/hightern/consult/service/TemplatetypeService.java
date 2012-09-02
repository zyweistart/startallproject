/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.consult.model.Templatetype;

public interface TemplatetypeService extends BaseService<Templatetype, Long> {
	  /***
	   * 获取模板数据
	   * @param status 1：用户可用 2：用户不可用
	   * @return
	   */
	  public List<Templatetype> obtainMag(Integer status);
}