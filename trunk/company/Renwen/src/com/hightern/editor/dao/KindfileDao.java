/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.editor.dao;


import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.editor.model.Kindfile;

public interface KindfileDao extends BaseDao<Kindfile, Long> {
	  public Boolean forManager(Long userId,String fileName,String fileType);
	  public Boolean forManager(String IP,String fileName,String fileType);
	  public Boolean judgeManager(String IP,String fileDate,String fileType);
	  public Boolean judgeManager(Long userID,String fileDate,String fileType);
	  
	  public List<Kindfile> hqMsg(Long userid,String userName);
}
