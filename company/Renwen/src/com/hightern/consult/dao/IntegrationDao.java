/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.consult.dao;

import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.consult.model.Integration;

public interface IntegrationDao extends BaseDao<Integration, Long> {
	public void updatexcrj(Integer xcrj);
	
	public String setNewsImg(String content);
	
	public String setNewsImg1(String content);

	public List<Integration> getnews();
	
	//获取integration信息
	public List<Integration> getInformation(Long id,Long pid,Integer size);

    public List<Integration> hqcontent(Long id,Long pid ,Integer tzStart ,Integer size);
    
    
    public List<Integration> getgg(Integer size);
    
    
    public Integration getGsnr();
    
    public List<Integration> getInform(Long id,Long category,Integer size);
}
