/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.ckman.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.ckman.dao.FwlDao;
import com.hightern.ckman.model.Fwl;
import com.hightern.ckman.service.FwlService;

@Service("fwlService")
@Transactional
public class FwlServiceImpl extends BaseServiceImpl<Fwl, Long>
        implements FwlService {

    @SuppressWarnings("unused")
    private final FwlDao fwlDao;

    @Autowired(required = false)
    public FwlServiceImpl(@Qualifier("fwlDao") FwlDao fwlDao) {
        super(fwlDao);
        this.fwlDao = fwlDao;
    }
    
    public Fwl into(){
    	Fwl f = this.findById(1L);
    	
    	f.setLs(f.getLs()+1L);
    	f = this.save(f);
    	return f;
    } 
}
