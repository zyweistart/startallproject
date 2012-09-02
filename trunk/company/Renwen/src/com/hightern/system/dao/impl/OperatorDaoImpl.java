/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.system.dao.OperatorDao;
import com.hightern.system.model.Operator;

@Repository("operatorDao")
public class OperatorDaoImpl extends BaseDaoImpl<Operator, Long> implements
        OperatorDao {
   
    public OperatorDaoImpl() {
        super(Operator.class, "operator");
    }
    
}
