/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.website.dao.MenuDao;
import com.hightern.website.model.Menu;
import com.hightern.website.service.MenuService;

@Service("menuService")
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long>
        implements MenuService {

    private final MenuDao menuDao;

    @Autowired(required = false)
    public MenuServiceImpl(@Qualifier("menuDao") MenuDao menuDao) {
        super(menuDao);
        this.menuDao = menuDao;
    }

    
    private final static String JPQL_findMenuByLevel="select o from menu o where o.level=? order by o.sort desc";
	@Override
	public List<Menu> findMenuByLevel(Integer level){
		return menuDao.queryForObject(JPQL_findMenuByLevel,new Object[]{level});
	}
	
	 private final static String JPQL_findMenuByParentId="select o from menu o where o.pid=? order by o.sort desc";
	@Override
	public List<Menu> findMenuByParentId(Long parentId) {
		return menuDao.queryForObject(JPQL_findMenuByParentId,new Object[]{parentId});
	}
    
}
