/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.system.dao.LinkDao;
import com.hightern.system.model.Link;
import com.hightern.system.service.LinkService;

@Service("linkService")
@Transactional
public class LinkServiceImpl extends BaseServiceImpl<Link, Long>
        implements LinkService {

    private final LinkDao linkDao;

    @Autowired(required = false)
    public LinkServiceImpl(@Qualifier("linkDao") LinkDao linkDao) {
        super(linkDao);
        this.linkDao = linkDao;
    }
    public List<Link> getHomeLink(){
    	return linkDao.getHomeLink();
    }
}
