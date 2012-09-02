/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.website.dao.HyperlinkDao;
import com.hightern.website.model.Hyperlink;

@Repository("hyperlinkDao")
public class HyperlinkDaoImpl extends BaseDaoImpl<Hyperlink, Long> implements
        HyperlinkDao {
   
    public HyperlinkDaoImpl() {
        super(Hyperlink.class, "hyperlink");
    }
    
}
