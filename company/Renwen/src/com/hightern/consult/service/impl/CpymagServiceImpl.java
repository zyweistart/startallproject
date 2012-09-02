/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.consult.dao.CpymagDao;
import com.hightern.consult.model.Cpymag;
import com.hightern.consult.service.CpymagService;

@Service("cpymagService")
@Transactional
public class CpymagServiceImpl extends BaseServiceImpl<Cpymag, Long>
        implements CpymagService {

    private final CpymagDao cpymagDao;

    @Autowired(required = false)
    public CpymagServiceImpl(@Qualifier("cpymagDao") CpymagDao cpymagDao) {
        super(cpymagDao);
        this.cpymagDao = cpymagDao;
    }
    
    public Cpymag hqContact(){
    	return cpymagDao.hqContact();
    }
    
}
