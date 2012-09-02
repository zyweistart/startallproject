/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.consult.dao.CpymagDao;
import com.hightern.consult.model.Cpymag;

@Repository("cpymagDao")
public class CpymagDaoImpl extends BaseDaoImpl<Cpymag, Long> implements
        CpymagDao {
   
    public CpymagDaoImpl() {
        super(Cpymag.class, "cpymag");
    }
    
    /***
     *  获取联系方式
     * @return Cpymag
     */
    @SuppressWarnings("unchecked")
    public Cpymag hqContact(){
    	List<Cpymag> cpymags=this.getEntityManager().createQuery("select o from cpymag o where 1=1 order by o.id asc").getResultList();
    	if(cpymags!=null && cpymags.size()>0){
    		return cpymags.get(0);
    	}else{
    	return new Cpymag();
    	} 
    }
    
    
}
