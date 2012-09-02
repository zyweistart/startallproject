/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.system.dao.AnnexDao;
import com.hightern.system.model.Annex;

@Repository("annexDao")
public class AnnexDaoImpl extends BaseDaoImpl<Annex, Long> implements
        AnnexDao {
   
    public AnnexDaoImpl() {
        super(Annex.class, "annex");
    }
    
}
