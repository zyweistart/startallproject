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
import com.hightern.schman.dao.FdbmagDao;
import com.hightern.schman.model.Fdbmag;
import com.hightern.schman.service.FdbmagService;

@Service("fdbmagService")
@Transactional
public class FdbmagServiceImpl extends BaseServiceImpl<Fdbmag, Long>
        implements FdbmagService {

    @SuppressWarnings("unused")
    private final FdbmagDao fdbmagDao;

    @Autowired(required = false)
    public FdbmagServiceImpl(@Qualifier("fdbmagDao") FdbmagDao fdbmagDao) {
        super(fdbmagDao);
        this.fdbmagDao = fdbmagDao;
    }
    
}
