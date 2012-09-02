/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.ckman.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.ckman.dao.FwlDao;
import com.hightern.ckman.model.Fwl;

@Repository("fwlDao")
public class FwlDaoImpl extends BaseDaoImpl<Fwl, Long> implements
        FwlDao {
   
    public FwlDaoImpl() {
        super(Fwl.class, "fwl");
    }
    
    public Long getsl(){
    	
     return null;    	
    }
    
    
}
