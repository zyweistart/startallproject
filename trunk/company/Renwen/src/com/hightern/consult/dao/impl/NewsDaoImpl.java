/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.consult.dao.NewsDao;
import com.hightern.consult.model.News;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDaoImpl<News, Long> implements
        NewsDao {
   
    public NewsDaoImpl() {
        super(News.class, "news");
    }
    
}
