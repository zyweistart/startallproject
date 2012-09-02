/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.test.dao.CsDao;
import com.hightern.test.model.Cs;
import com.hightern.test.service.CsService;

@Service("csService")
@Transactional
public class CsServiceImpl extends BaseServiceImpl<Cs, Long>
        implements CsService {

   
    private final CsDao csDao;

    @Autowired(required = false)
    public CsServiceImpl(@Qualifier("csDao") CsDao csDao) {
        super(csDao);
        this.csDao = csDao;
    }
  
    public void updateStort(){
    	csDao.updateStort();
    }
    
    
}
