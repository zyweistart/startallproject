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
import com.hightern.schman.dao.FbreplyDao;
import com.hightern.schman.model.Fbreply;
import com.hightern.schman.service.FbreplyService;

@Service("fbreplyService")
@Transactional
public class FbreplyServiceImpl extends BaseServiceImpl<Fbreply, Long>
        implements FbreplyService {

    private final FbreplyDao fbreplyDao;

    @Autowired(required = false)
    public FbreplyServiceImpl(@Qualifier("fbreplyDao") FbreplyDao fbreplyDao) {
        super(fbreplyDao);
        this.fbreplyDao = fbreplyDao;
    }
    
    public List<Fbreply> forMag(Long potsId){
    	return fbreplyDao.forMag(potsId);
    }
    
}
