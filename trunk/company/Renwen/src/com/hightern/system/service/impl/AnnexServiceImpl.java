/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.system.dao.AnnexDao;
import com.hightern.system.model.Annex;
import com.hightern.system.service.AnnexService;

@Service("annexService")
@Transactional
public class AnnexServiceImpl extends BaseServiceImpl<Annex, Long>
        implements AnnexService {

    @SuppressWarnings("unused")
    private final AnnexDao annexDao;

    @Autowired(required = false)
    public AnnexServiceImpl(@Qualifier("annexDao") AnnexDao annexDao) {
        super(annexDao);
        this.annexDao = annexDao;
    }
    
}
