/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.system.dao.ModelDao;
import com.hightern.system.model.Model;

@Repository("modelDao")
public class ModelDaoImpl extends BaseDaoImpl<Model, Long> implements
        ModelDao {
	
	private static final String JPQL_QUERY_MODELS_PARENID = "select o from model o where o.parentId =:parentId";
   
    public ModelDaoImpl() {
        super(Model.class, "model");
    }

	@SuppressWarnings("unchecked")
	public List<Model> getChildren(Long id) {
		return this.getEntityManager().createQuery(JPQL_QUERY_MODELS_PARENID).setParameter("parentId", id).getResultList();
	}
    
}
