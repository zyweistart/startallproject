/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.ckman.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.ckman.dao.GjzDao;
import com.hightern.ckman.model.Gjz;

@Repository("gjzDao")
public class GjzDaoImpl extends BaseDaoImpl<Gjz, Long> implements
        GjzDao {
   
    public GjzDaoImpl() {
        super(Gjz.class, "gjz");
    }
    
}
