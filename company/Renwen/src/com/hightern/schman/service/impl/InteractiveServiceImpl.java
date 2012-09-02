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
import com.hightern.schman.dao.InteractiveDao;
import com.hightern.schman.model.Interactive;
import com.hightern.schman.service.InteractiveService;

@Service("interactiveService")
@Transactional
public class InteractiveServiceImpl extends BaseServiceImpl<Interactive, Long>
        implements InteractiveService {

    @SuppressWarnings("unused")
    private final InteractiveDao interactiveDao;

    @Autowired(required = false)
    public InteractiveServiceImpl(@Qualifier("interactiveDao") InteractiveDao interactiveDao) {
        super(interactiveDao);
        this.interactiveDao = interactiveDao;
    }
    
}
