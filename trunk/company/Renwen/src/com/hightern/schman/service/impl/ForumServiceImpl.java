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
import com.hightern.schman.dao.ForumDao;
import com.hightern.schman.model.Forum;
import com.hightern.schman.service.ForumService;

@Service("forumService")
@Transactional
public class ForumServiceImpl extends BaseServiceImpl<Forum, Long>
        implements ForumService {

    @SuppressWarnings("unused")
    private final ForumDao forumDao;

    @Autowired(required = false)
    public ForumServiceImpl(@Qualifier("forumDao") ForumDao forumDao) {
        super(forumDao);
        this.forumDao = forumDao;
    }
    
}
