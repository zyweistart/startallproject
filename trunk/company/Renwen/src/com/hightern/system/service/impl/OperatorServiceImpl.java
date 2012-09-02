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
import com.hightern.system.dao.OperatorDao;
import com.hightern.system.model.Operator;
import com.hightern.system.service.OperatorService;

@Service("operatorService")
@Transactional
public class OperatorServiceImpl extends BaseServiceImpl<Operator, Long>
        implements OperatorService {

    @SuppressWarnings("unused")
    private final OperatorDao operatorDao;

    @Autowired(required = false)
    public OperatorServiceImpl(@Qualifier("operatorDao") OperatorDao operatorDao) {
        super(operatorDao);
        this.operatorDao = operatorDao;
    }
    
}
