/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.editor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.editor.dao.KindfileDao;
import com.hightern.editor.model.Kindfile;

@Repository("kindfileDao")
public class KindfileDaoImpl extends BaseDaoImpl<Kindfile, Long> implements
        KindfileDao {
   
    public KindfileDaoImpl() {
        super(Kindfile.class, "kindfile");
    }
    //判断登录用户，该文件是否为该用户上传
    @SuppressWarnings("unchecked")
    public Boolean forManager(Long userId,String fileName,String fileType){
    	boolean flty=false;
    	List<Kindfile> kindfiles=this.getEntityManager().createNativeQuery("select * from  EDI_Kindfile where userID=? and fileName=? and fileType=?").setParameter(1, userId).setParameter(2, fileName).setParameter(3, fileType).getResultList();
            	if(kindfiles!=null && kindfiles.size()>0){
            		flty=true;
            	}
    	return flty;
    }
    //判断IP用户，该文件是否为该用户上传
    @SuppressWarnings("unchecked")
    public Boolean forManager(String IP,String fileName,String fileType){
    	boolean flty=false;
    	List<Kindfile> kindfiles=this.getEntityManager().createNativeQuery("select * from  EDI_Kindfile where userName=? and fileName=? and fileType=?").setParameter(1, IP).setParameter(2, fileName).setParameter(3, fileType).getResultList();
            	if(kindfiles!=null && kindfiles.size()>0){
            		flty=true;
            	}
    	return flty;
    }
    
    //判断登录用户，该文件夹下面是否上传过信息
    @SuppressWarnings("unchecked")
    public Boolean judgeManager(Long userID,String fileDate,String fileType){
    	boolean flty=false;
    	List<Kindfile> kindfiles=this.getEntityManager().createNativeQuery("select * from  EDI_Kindfile where userID=? and fileDate=? and fileType=?").setParameter(1, userID).setParameter(2, fileDate).setParameter(3, fileType).getResultList();
            	if(kindfiles!=null && kindfiles.size()>0){
            		flty=true;
            	}
    	return flty;
    }
    
    //判断IP用户，该文件是否为该用户上传
    @SuppressWarnings("unchecked")
    public Boolean judgeManager(String IP,String fileDate,String fileType){
    	boolean flty=false;
    	List<Kindfile> kindfiles=this.getEntityManager().createNativeQuery("select * from  EDI_Kindfile where userName=? and fileDate=? and fileType=?").setParameter(1, IP).setParameter(2, fileDate).setParameter(3, fileType).getResultList();
            	if(kindfiles!=null && kindfiles.size()>0){
            		flty=true;
            	}
    	return flty;
    }
    
    //获取用户管理的文件
    @SuppressWarnings("unchecked")
	public List<Kindfile> hqMsg(Long userid,String userName){
       List<Kindfile> kindfiles=this.getEntityManager().createNativeQuery("select * from EDI_Kindfile where userID=? or (userID=0 and userName=?)").setParameter(1, userid).setParameter(2, userName).getResultList();
       return kindfiles;
    }
    
    
}
