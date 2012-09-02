/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.website.dao.MenuDao;
import com.hightern.website.model.Menu;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu, Long> implements
        MenuDao {
   
    public MenuDaoImpl() {
        super(Menu.class, "menu");
    }
    
}
