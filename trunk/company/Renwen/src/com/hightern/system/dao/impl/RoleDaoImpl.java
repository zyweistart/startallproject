/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.system.dao.RoleDao;
import com.hightern.system.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements
        RoleDao {
   
    public RoleDaoImpl() {
        super(Role.class, "role");
    }
    
}
