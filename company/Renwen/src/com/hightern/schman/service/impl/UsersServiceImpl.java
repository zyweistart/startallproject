/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.schman.dao.UsersDao;
import com.hightern.schman.model.Users;
import com.hightern.schman.service.UsersService;

@Service("usersService")
@Transactional
public class UsersServiceImpl extends BaseServiceImpl<Users, Long>
        implements UsersService {

    private final UsersDao usersDao;

    @Autowired(required = false)
    public UsersServiceImpl(@Qualifier("usersDao") UsersDao usersDao) {
        super(usersDao);
        this.usersDao = usersDao;
    }
    
    public boolean judgeName(String userName){
    	return usersDao.judgeName(userName);
    }
    
    public List<Users> hquserMag(String userName,String password){
    	return usersDao.hquserMag(userName, password);
    }
}
