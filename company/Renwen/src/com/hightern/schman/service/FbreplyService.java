/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.service;

import java.util.List;

import com.hightern.kernel.service.BaseService;
import com.hightern.schman.model.Fbreply;

public interface FbreplyService extends BaseService<Fbreply, Long> {
     /***
      * 获取回复的帖子
      * @param potsId  帖子ID
      * @return
      */
	 public List<Fbreply> forMag(Long potsId);
}