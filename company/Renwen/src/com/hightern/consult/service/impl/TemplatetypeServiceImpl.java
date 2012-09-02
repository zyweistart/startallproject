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
import com.hightern.consult.dao.TemplatetypeDao;
import com.hightern.consult.model.Templatetype;
import com.hightern.consult.service.TemplatetypeService;

@Service("templatetypeService")
@Transactional
public class TemplatetypeServiceImpl extends BaseServiceImpl<Templatetype, Long>
        implements TemplatetypeService {

    private final TemplatetypeDao templatetypeDao;

    @Autowired(required = false)
    public TemplatetypeServiceImpl(@Qualifier("templatetypeDao") TemplatetypeDao templatetypeDao) {
        super(templatetypeDao);
        this.templatetypeDao = templatetypeDao;
    }
    
    public List<Templatetype> obtainMag(Integer status){
    	return templatetypeDao.obtainMag(status);
    }
    
}
