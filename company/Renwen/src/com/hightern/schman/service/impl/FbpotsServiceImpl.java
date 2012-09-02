/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.schman.dao.FbpotsDao;
import com.hightern.schman.model.Fbpots;
import com.hightern.schman.service.FbpotsService;

@Service("fbpotsService")
@Transactional
public class FbpotsServiceImpl extends BaseServiceImpl<Fbpots, Long>
        implements FbpotsService {

    @SuppressWarnings("unused")
    private final FbpotsDao fbpotsDao;

    @Autowired(required = false)
    public FbpotsServiceImpl(@Qualifier("fbpotsDao") FbpotsDao fbpotsDao) {
        super(fbpotsDao);
        this.fbpotsDao = fbpotsDao;
    }
    
}
