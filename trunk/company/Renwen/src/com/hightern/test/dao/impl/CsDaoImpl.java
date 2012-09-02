/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.test.dao.impl;


import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.test.dao.CsDao;
import com.hightern.test.model.Cs;

@Repository("csDao")
public class CsDaoImpl extends BaseDaoImpl<Cs, Long> implements
        CsDao {
   
    public CsDaoImpl() {
        super(Cs.class, "cs");
    }
    
    
    /**
     * 更新状态
     */
    public void updateStort(){
    	this.getEntityManager().createQuery("update cs o set o.stort=?").setParameter(1, "久版本").executeUpdate();
    }
    


    
}
