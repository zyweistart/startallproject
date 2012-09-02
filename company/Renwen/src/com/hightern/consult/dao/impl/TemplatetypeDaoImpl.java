/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.consult.dao.TemplatetypeDao;
import com.hightern.consult.model.Templatetype;

@Repository("templatetypeDao")
public class TemplatetypeDaoImpl extends BaseDaoImpl<Templatetype, Long> implements
        TemplatetypeDao {
   
    public TemplatetypeDaoImpl() {
        super(Templatetype.class, "templatetype");
    }
    
    
    /**
     * 获取模板数据
     * @param status 1：用户可用 2：用户不可用
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Templatetype> obtainMag(Integer status){
       String jpql="";
    	if(status==1){
         jpql="select o from templatetype o  where o.operatStatus=1";  	
        }else{
         jpql="select o from templatetype o  where o.operatStatus=2";        	
        }
        
    	List<Templatetype> templatetypes=this.getEntityManager().createQuery(jpql).getResultList();
    	
    	return templatetypes;
    }
    
    
}
