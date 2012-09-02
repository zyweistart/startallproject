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
import com.hightern.system.dao.PostsDao;
import com.hightern.system.model.Posts;
import com.hightern.system.service.PostsService;

@Service("postsService")
@Transactional
public class PostsServiceImpl extends BaseServiceImpl<Posts, Long>
        implements PostsService {

    @SuppressWarnings("unused")
    private final PostsDao postsDao;

    @Autowired(required = false)
    public PostsServiceImpl(@Qualifier("postsDao") PostsDao postsDao) {
        super(postsDao);
        this.postsDao = postsDao;
    }
    
}
