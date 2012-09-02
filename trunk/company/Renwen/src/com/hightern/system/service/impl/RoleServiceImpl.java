/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.system.dao.RoleDao;
import com.hightern.system.model.Role;
import com.hightern.system.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, Long>
        implements RoleService {

    @SuppressWarnings("unused")
    private final RoleDao roleDao;

    @Autowired(required = false)
    public RoleServiceImpl(@Qualifier("roleDao") RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }
    
}
