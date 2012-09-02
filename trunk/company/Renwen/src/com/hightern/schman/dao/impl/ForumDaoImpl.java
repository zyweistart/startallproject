/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.schman.dao.ForumDao;
import com.hightern.schman.model.Forum;

@Repository("forumDao")
public class ForumDaoImpl extends BaseDaoImpl<Forum, Long> implements
        ForumDao {
   
    public ForumDaoImpl() {
        super(Forum.class, "forum");
    }
    
}
