/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.consult.dao.IntegrationDao;
import com.hightern.consult.model.Integration;
import com.hightern.consult.service.IntegrationService;

@Service("integrationService")
@Transactional
public class IntegrationServiceImpl extends BaseServiceImpl<Integration, Long>
        implements IntegrationService {

    private final IntegrationDao integrationDao;

    @Autowired(required = false)
    public IntegrationServiceImpl(@Qualifier("integrationDao") IntegrationDao integrationDao) {
        super(integrationDao);
        this.integrationDao = integrationDao;
    }
    public void updatexcrj(Integer xcrj){
          integrationDao.updatexcrj(xcrj);
    }
    
    
    public String setNewsImg(String content)
    {
    	return integrationDao.setNewsImg(content);
    }
    public String setNewsImg1(String content)
    {
    	return integrationDao.setNewsImg1(content);
    }

    public List<Integration> getnews()
    {
    	return integrationDao.getnews();
    }
    
    //获取integration信息
	public List<Integration> getInformation(Long id,Long pid,Integer size){
		return integrationDao.getInformation(id, pid, size);
	}
	
	 public List<Integration> hqcontent(Long id,Long pid ,Integer tzStart ,Integer size){
		 return integrationDao.hqcontent(id, pid, tzStart, size);
	 }
	
	 public List<Integration> getgg(Integer size){
		 return integrationDao.getgg(size);
	 }
	 
	 public Integration getGsnr(){
		 return integrationDao.getGsnr();
	 }
	 
	 
	 public List<Integration> getInform(Long id,Long category,Integer size){
		 return integrationDao.getInform(id, category, size);
	 }
	 
	 
}
