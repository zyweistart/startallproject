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
import com.hightern.system.dao.LogsDao;
import com.hightern.system.model.Logs;
import com.hightern.system.service.LogsService;

@Service("logsService")
@Transactional
public class LogsServiceImpl extends BaseServiceImpl<Logs, Long>
        implements LogsService {

    @SuppressWarnings("unused")
    private final LogsDao logsDao;

    @Autowired(required = false)
    public LogsServiceImpl(@Qualifier("logsDao") LogsDao logsDao) {
        super(logsDao);
        this.logsDao = logsDao;
    }
    
}
