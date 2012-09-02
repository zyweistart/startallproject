/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hightern.kernel.dao.impl.BaseDaoImpl;
import com.hightern.consult.dao.NewstypeDao;
import com.hightern.consult.model.Newstype;

@Repository("newstypeDao")
public class NewstypeDaoImpl extends BaseDaoImpl<Newstype, Long> implements
		NewstypeDao {

	public NewstypeDaoImpl() {
		super(Newstype.class, "newstype");
	}

	// 判断是否含有子类
	@SuppressWarnings("rawtypes")
	public boolean hasChild(List<Long> ids) {
		boolean d = false;
		for (Long id : ids) {
		
			List list = this.getEntityManager()
					.createQuery("select o from newstype o where o.pid = ?")
					.setParameter(1, id).getResultList();
			if (list.size() > 0) {
				d = true;
				break;
			}
		}
		return d;
	}

	// 查找子类
	@SuppressWarnings("unchecked")
	public List<Newstype> findByPid(Long pid) {
		List<Newstype> list = this.getEntityManager()
				.createQuery("select o from newstype o where o.tstart=99 and o.pid =?")
				.setParameter(1, pid).getResultList();
		if (list != null && list.size() > 0) {
			for (Newstype nt : list) {
				List<Newstype> list1 = this.findByPid(nt.getId());
				if (list1 != null && list1.size() > 0) {
					nt.setChildren(list1);
				}
			}
		}
		return list;
	}

	//
	@SuppressWarnings("unchecked")
	public List<Newstype> getchilren(Long pid) {
		return this.getEntityManager()
				.createQuery("select o from newstype o where o.pid =?")
				.setParameter(1, pid).getResultList();
	}

	public void updateName(Long id,Long pid,Long category, String name) {
		this.getEntityManager()
				.createQuery(
						"update integration o set o.newsTypeName =? , o.parentNewsTypeId=? , o.category=?   where o.newsTypeId =?")
				.setParameter(1, name).setParameter(2,pid).setParameter(3,category).setParameter(4, id).executeUpdate();
	}

	//导航
	@SuppressWarnings("unchecked")
	public List<Newstype> getMapName(Long id)
	{
		return this.getEntityManager().createQuery("select o from newstype o where o.pid=?").setParameter(1, id).getResultList();
	}
	
	/**
	 * 首页菜单导航  (状态为首页的才展示)
	 * @param id
	 * @return
	 */
	//首页菜单导航
	@SuppressWarnings("unchecked")
	public List<Newstype> getMenus(Long id)
	{
		return this.getEntityManager().createQuery("select o from newstype o where o.tstart=99 and o.pid=?").setParameter(1, id).getResultList();
	}
	
	
	
	//导航
	@SuppressWarnings("unchecked")
	public List<Newstype> getMapName(Long id,int size)
	{
		return this.getEntityManager().createQuery("select o from newstype o where o.pid=?").setParameter(1, id).setFirstResult(0).setMaxResults(size).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Newstype> getMapName(Long id,int qsize,int size)
	{
		return this.getEntityManager().createQuery("select o from newstype o where o.pid=?").setParameter(1, id).setFirstResult(qsize).setMaxResults(size).getResultList();
	}
	
	
	//查父节点
	@SuppressWarnings("unchecked")
	public List<Newstype> getFuJiedian(Long id)
	{
		return this.getEntityManager().createQuery("select o from newstype o where o.id=?").setParameter(1, id).getResultList();
	}
	
	/***
	 * 取得顶级目录，模板类别为不是无的数据
	 * @param category 设置类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Newstype> obtainNewstype(Long category){
		List<Newstype> newstypes=this.getEntityManager().createQuery("select o from newstype o where o.pid=0 and o.category!="+category).getResultList();
		return newstypes;
	}
	
	

}
