/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.consult.dao.NewsDao;
import com.hightern.consult.model.News;
import com.hightern.consult.service.NewsService;

@Service("newsService")
@Transactional
public class NewsServiceImpl extends BaseServiceImpl<News, Long>
        implements NewsService {

    @SuppressWarnings("unused")
    private final NewsDao newsDao;

    @Autowired(required = false)
    public NewsServiceImpl(@Qualifier("newsDao") NewsDao newsDao) {
        super(newsDao);
        this.newsDao = newsDao;
    }
    
}
