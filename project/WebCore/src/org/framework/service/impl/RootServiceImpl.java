package org.framework.service.impl;

import java.io.Serializable;
import java.util.List;

import org.framework.dao.RootDao;
import org.framework.entity.Root;
import org.framework.exception.AppException;
import org.framework.exception.AppRuntimeException;
import org.framework.service.RootService;
import org.message.IMsg;
import org.zyweistartframework.persistence.ds.Transaction;

public class RootServiceImpl<T extends Root, PK extends Serializable>
		implements RootService<T, PK> {
	
	private final RootDao<T,PK> rootDao;
	
	public RootServiceImpl(RootDao<T,PK> rootDao){
		this.rootDao=rootDao;
	}

	@Override
	public void save(T entity) {
		rootDao.save(entity,entity.getId()==null);
	}

	@Override
	public void remove(T entity) {
		rootDao.remove(entity);
	}

	@Override
	public T load(PK id) {
		return rootDao.load(id, true);
	}
	
	@Override
	public T lazyLoad(PK id) {
		return rootDao.load(id, false);
	}
	
	@Override
	public T getResultListOnlySingle(String nSql, Object... params) {
		List<T> collections=getResultList(nSql,params);
		if(!collections.isEmpty()){
			if(collections.size()==1){
				return collections.get(0);
			}
			//数据出现异常
			throw new AppRuntimeException(IMsg._1005);
		}
		return null;
	}

	@Override
	public List<T> getResultList(String nSql, Object... params){
		return getResultList(nSql,null,null,params);
	}

	@Override
	public List<T> getResultList(String nSql, Integer firstResult, Integer maxResult,Object... params){
		return rootDao.getResultList(nSql, firstResult, maxResult, params);
	}
	

	@Override
	public int getSingleResult(String nSql, Object... params) {
		return rootDao.getSingleResult(nSql, params);
	}
	
	@Override
	public T createNativeSQLOnlySingle(String nativeSQL, Object... params){
		List<T> collections=createNativeSQL(nativeSQL,params);
		if(!collections.isEmpty()){
			if(collections.size()==1){
				return collections.get(0);
			}
			//数据出现异常
			throw new AppRuntimeException(IMsg._1005);
		}
		return null;
	}

	@Override
	public List<T> createNativeSQL(String nativeSQL, Object... params) {
		return rootDao.createNativeSQL(nativeSQL, params);
	}

	@Override
	public Transaction getTransient() {
		return rootDao.getTransient();
	}
	
	@Override
	public void closeConnection() throws AppException {
		rootDao.closeConnection();
	}
	
}