/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.website.dao.HyperlinkDao;
import com.hightern.website.model.Hyperlink;
import com.hightern.website.service.HyperlinkService;

@Service("hyperlinkService")
@Transactional
public class HyperlinkServiceImpl extends BaseServiceImpl<Hyperlink, Long>
        implements HyperlinkService {

    @SuppressWarnings("unused")
    private final HyperlinkDao hyperlinkDao;

    @Autowired(required = false)
    public HyperlinkServiceImpl(@Qualifier("hyperlinkDao") HyperlinkDao hyperlinkDao) {
        super(hyperlinkDao);
        this.hyperlinkDao = hyperlinkDao;
    }
    
}
