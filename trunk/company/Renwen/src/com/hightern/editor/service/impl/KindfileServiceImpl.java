/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.editor.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.editor.dao.KindfileDao;
import com.hightern.editor.model.Kindfile;
import com.hightern.editor.service.KindfileService;

@Service("kindfileService")
@Transactional
public class KindfileServiceImpl extends BaseServiceImpl<Kindfile, Long>
        implements KindfileService {

    private final KindfileDao kindfileDao;

    @Autowired(required = false)
    public KindfileServiceImpl(@Qualifier("kindfileDao") KindfileDao kindfileDao) {
        super(kindfileDao);
        this.kindfileDao = kindfileDao;
    }
    
    
    public Boolean forManager(Long userId,String fileName,String fileType){
    	return kindfileDao.forManager(userId,fileName,fileType);
    }
    
    public Boolean forManager(String IP,String fileName,String fileType){
    	return kindfileDao.forManager(IP, fileName, fileType);
    }
    
    public Boolean judgeManager(Long userID,String fileDate,String fileType){
    	return kindfileDao.judgeManager(userID, fileDate, fileType);
    }
    
    public Boolean judgeManager(String IP,String fileDate,String fileType){
    	return kindfileDao.judgeManager(IP, fileDate, fileType);
    }
    
    public List<Kindfile> hqMsg(Long userid,String userName){
    	return kindfileDao.hqMsg(userid, userName);
    }
}
