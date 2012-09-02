/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.system.dao.ModelDao;
import com.hightern.system.model.Model;
import com.hightern.system.service.ModelService;

@Service("modelService")
@Transactional
public class ModelServiceImpl extends BaseServiceImpl<Model, Long>
        implements ModelService {

    private final ModelDao modelDao;

    @Autowired(required = false)
    public ModelServiceImpl(@Qualifier("modelDao") ModelDao modelDao) {
        super(modelDao);
        this.modelDao = modelDao;
    }

	public List<Model> getChildrenModels(Long id) {
		List<Model> models = modelDao.getChildren(id);
		if(models != null && models.size()>0){
			for (Model model : models) {
				List<Model> datas = this.getChildrenModels(model.getId());
				if(datas != null && datas.size()>0){
					model.setChildren(datas);
				}
			}
		}
		return models;
	}
}
