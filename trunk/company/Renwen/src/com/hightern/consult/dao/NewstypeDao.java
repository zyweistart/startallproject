/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.consult.dao;

import java.util.List;

import com.hightern.kernel.dao.BaseDao;
import com.hightern.consult.model.Newstype;

public interface NewstypeDao extends BaseDao<Newstype, Long> {
 
	//判断是否含有子类
    public boolean hasChild(List<Long> ids);
    //首页菜单导航
    public List<Newstype> getMenus(Long id);
    
    //查找子类
    public List<Newstype> findByPid(Long pid);
    
    public List<Newstype> getchilren(Long pid);
    
    public void updateName(Long id,Long pid,Long category, String name);
    
	public List<Newstype> getMapName(Long id);
	
	public List<Newstype> getFuJiedian(Long id);
	
	public List<Newstype> getMapName(Long id,int size);
	
	public List<Newstype> getMapName(Long id,int qsize,int size);
	
	public List<Newstype> obtainNewstype(Long category);
	
}
