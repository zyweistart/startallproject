/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.website.dao.InformationDao;
import com.hightern.website.model.Information;

@Repository("informationDao")
public class InformationDaoImpl extends BaseDaoImpl<Information, Long> implements
        InformationDao {
   
    public InformationDaoImpl() {
        super(Information.class, "information");
    }
    
}
