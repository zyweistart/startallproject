/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.schman.dao.VisitsDao;
import com.hightern.schman.model.Visits;
import com.hightern.schman.service.VisitsService;

@Service("visitsService")
@Transactional
public class VisitsServiceImpl extends BaseServiceImpl<Visits, Long>
        implements VisitsService {

    private final VisitsDao visitsDao;

    @Autowired(required = false)
    public VisitsServiceImpl(@Qualifier("visitsDao") VisitsDao visitsDao) {
        super(visitsDao);
        this.visitsDao = visitsDao;
    }
    
    public List<Visits> hqvisitsMag(Integer type,Long userId){
    	return visitsDao.hqvisitsMag(type, userId);
    }
    
    public String  hqvisitsMax(Integer type,Long userId){
    	return visitsDao.hqvisitsMax(type, userId);
    }
    
	public String  hqsizeMax(Integer type,Long userId){
		return visitsDao.hqsizeMax(type, userId);
	}
}
