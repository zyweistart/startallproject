/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.dao.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.schman.dao.VisitsDao;
import com.hightern.schman.model.Visits;

@Repository("visitsDao")
public class VisitsDaoImpl extends BaseDaoImpl<Visits, Long> implements
        VisitsDao {
   
    public VisitsDaoImpl() {
        super(Visits.class, "visits");
    }
    
    /***
     * 获取总访问量
     * @param type  1:总访问   2：测试库访问
     * @param userId 用户编号
     * @return
     */
    @SuppressWarnings("unchecked")
	public String  hqvisitsMax(Integer type,Long userId){
    	Float max=new Float(0);
    	DecimalFormat   ss   =   new   DecimalFormat( "#0.00 ");   
        List<Visits> visitss=this.getEntityManager().createQuery("select o from visits o where o.type=? and o.userID=?").setParameter(1, type).setParameter(2, userId).getResultList();
        if(visitss!=null && visitss.size()>0L){
        	for (Visits visits : visitss) {
        		if(visits.getRecordSize()!=null && visits.getRecordSize().length()>0L){
				max+=Float.parseFloat(visits.getRecordSize());
				}
			}
        }
        return ss.format(max).toString();
    }
    
    /***
     * 获取访问次数
     * @param type  1:总访问   2：测试库访问
     * @param userId 用户编号
     * @return
     */
    @SuppressWarnings("unchecked")
	public String  hqsizeMax(Integer type,Long userId){
    	Integer max=new Integer(0);
        List<Visits> visitss=this.getEntityManager().createQuery("select o from visits o where o.type=? and o.userID=?").setParameter(1, type).setParameter(2, userId).getResultList();
        if(visitss!=null && visitss.size()>0L){
        	max=visitss.size();
        }
        return max.toString();
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Visits> hqvisitsMag(Integer type,Long userId){
    	List<Visits> visitss=this.getEntityManager().createQuery("select o from visits o where o.type=? and o.userID=? order by o.id desc").setParameter(1, type).setParameter(2, userId).getResultList();
    	return visitss;
    }
    
    
    
    
}
