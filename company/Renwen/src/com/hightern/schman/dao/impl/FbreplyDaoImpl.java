/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.schman.dao.FbreplyDao;
import com.hightern.schman.model.Fbreply;

@Repository("fbreplyDao")
public class FbreplyDaoImpl extends BaseDaoImpl<Fbreply, Long> implements
        FbreplyDao {
   
    public FbreplyDaoImpl() {
        super(Fbreply.class, "fbreply");
    }
    
    @SuppressWarnings("unchecked")
    public List<Fbreply> forMag(Long potsId){
    	List<Fbreply> fbreplies=this.getEntityManager().createQuery("select o from fbreply o where o.potsId=?").setParameter(1, potsId).getResultList();
    	return fbreplies;
    }
    
    
    
}
