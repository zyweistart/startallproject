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
import com.hightern.ckman.dao.GjzDao;
import com.hightern.ckman.model.Gjz;
import com.hightern.ckman.service.GjzService;

@Service("gjzService")
@Transactional
public class GjzServiceImpl extends BaseServiceImpl<Gjz, Long>
        implements GjzService {

    @SuppressWarnings("unused")
    private final GjzDao gjzDao;

    @Autowired(required = false)
    public GjzServiceImpl(@Qualifier("gjzDao") GjzDao gjzDao) {
        super(gjzDao);
        this.gjzDao = gjzDao;
    }
    
}
