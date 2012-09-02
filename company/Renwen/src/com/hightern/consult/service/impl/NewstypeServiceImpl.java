/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hightern.kernel.service.impl.BaseServiceImpl;
import com.hightern.consult.dao.NewstypeDao;
import com.hightern.consult.model.Newstype;
import com.hightern.consult.service.NewstypeService;

@Service("newstypeService")
@Transactional
public class NewstypeServiceImpl extends BaseServiceImpl<Newstype, Long>
		implements NewstypeService {

	private final NewstypeDao newstypeDao;

	@Autowired(required = false)
	public NewstypeServiceImpl(@Qualifier("newstypeDao")
	NewstypeDao newstypeDao) {
		super(newstypeDao);
		this.newstypeDao = newstypeDao;
	}

	// 判断是否含有子类
	public boolean hasChild(List<Long> ids) {
		return newstypeDao.hasChild(ids);
	}

	// 查找子类
	public List<Newstype> findByPid(Long pid) {
		return newstypeDao.findByPid(pid);
	}

	public List<Newstype> getchilren(Long pid) {

		List<Newstype> newstypes = newstypeDao.getchilren(pid);
		List<Newstype> newstypess = new ArrayList<Newstype>();
		if (newstypes != null && newstypes.size() > 0) {
			for (Newstype n : newstypes) {
				List<Newstype> types = this.getchilren(n.getId());
				if (types != null && types.size() > 0) {
					n.setChildren(types);
					for (Newstype t : types) {
						newstypess.add(t);
					}
				}
				newstypess.add(n);
			}
		}
		return newstypess;
	}
	
	 //首页菜单导航
    public List<Newstype> getMenus(Long id){
    	return newstypeDao.getMenus(id);
    }
	
	
	
	public void updateName(Long id,Long pid,Long category, String name){
		newstypeDao.updateName(id, pid, category, name);
	}
	
	public List<Newstype> getMapName(Long id)
	{
		return newstypeDao.getMapName(id);
	}
	
	public List<Newstype> getFuJiedian(Long id)
	{
		return newstypeDao.getFuJiedian(id);
	}
	
	public List<Newstype> getMapName(Long id,int size)
	{
		return newstypeDao.getMapName(id, size);
	}
	
	public List<Newstype> getMapName(Long id,int qsize,int size)
	{
		return newstypeDao.getMapName(id, qsize, size);
	}
	
	public List<Newstype> obtainNewstype(Long category){
		return newstypeDao.obtainNewstype(category);
	}
	
	
}
