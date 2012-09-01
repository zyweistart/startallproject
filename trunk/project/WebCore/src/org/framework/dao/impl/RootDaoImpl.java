package org.framework.dao.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.framework.dao.RootDao;
import org.framework.entity.Root;
import org.framework.exception.AppException;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.LogUtils;
import org.message.IMsg;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.Query;
import org.zyweistartframework.persistence.annotation.PersistenceContext;
import org.zyweistartframework.persistence.ds.Session;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.persistence.factory.oracle.RowSet;
import org.zyweistartframework.utils.StackTraceInfo;

public class RootDaoImpl<T extends Root, PK extends Serializable>
		implements RootDao<T, PK> {
	
	private Class<T> prototype;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public RootDaoImpl(Class<T> prototype){
		this.prototype=prototype;
	}

	public Class<T> getPrototype() {
		return prototype;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void save(T entity,Boolean flag) {
		try{
			if(flag){
				getEntityManager().persist(entity);
			}else{
				getEntityManager().merge(entity);
			}
		}catch(Exception e){
     		LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._999);
		}
	}
	
	@Override
	public void remove(T entity) {
		try{
			getEntityManager().remove(entity);
		}catch(Exception e){
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._999);
		}
	}

	@Override
	public T load(PK id, Boolean flag) {
		try{
			return getEntityManager().load(getPrototype(), id,flag);
		}catch(Exception e){
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._999);
		}
	}

	@Override
	public List<T> getResultList(String nSql,Integer firstResult,Integer maxResult, Object... params){
		try{
			Query query=getEntityManager().createQuery(nSql);
			for(int i=0;i<params.length;i++){
				query.setParameter(i+1, params[i]);
			}
			//设置第一条记录的位置
			query.setFirstResult(firstResult);
			//设置返回的最大记录数
			query.setMaxResult(maxResult);
			return  query.getResultList();
		}catch(Exception e){
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._999);
		}
	}

	@Override
	public List<T> createNativeSQL(String nativeSQL, Object... params){
		try{
			return getEntityManager().createNativeSQL(getPrototype(), nativeSQL, params);
		}catch(Exception e){
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._999);
		}
	}
	
	@Override
	public int getSingleResult(String nSql, Object... params){
		try{
			Query query=getEntityManager().createQuery(nSql);
			for(int i=0;i<params.length;i++){
				query.setParameter(i+1, params[i]);
			}
			return query.getSingleResult();
		}catch(Exception e){
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._999);
		}
	}
	
	@Override
	public RowSet executeQuery(String executeQuery, Object... params){
		ResultSet rset=null;
		PreparedStatement pstmt=null;
		try {
			pstmt=getSession().executeQuery(executeQuery, params);
			rset=pstmt.executeQuery();
			RowSet rs=new RowSet();
			rs.populate(rset);
			return rs;
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + 
					Message.getMessage(Message.PM_5009,executeQuery,e.getMessage()));
			throw new AppRuntimeException(IMsg._999);
		}finally{
			Session.closeResultSet(rset);
			Session.closePreparedStatement(pstmt);
		}
	}
	
	@Override
	public int polymerizationQuery(String executeQuery,Object... params){
		ResultSet rset=null;
		PreparedStatement pstmt=null;
		try {
			pstmt=getSession().executeQuery(executeQuery, params);
			rset=pstmt.executeQuery();
			int count=0;
			if(rset.next()){
				count=rset.getInt(1);
			}
			return count;
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + 
					Message.getMessage(Message.PM_5009,executeQuery,e.getMessage()));
			throw new AppRuntimeException(IMsg._999);
		}finally{
			Session.closeResultSet(rset);
			Session.closePreparedStatement(pstmt);
		}
	}
	
	@Override
	public int executeUpdate(String executeUpdate, Object... params){
		try {
			return getSession().executeUpdate(executeUpdate, params);
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + 
					Message.getMessage(Message.PM_5009,executeUpdate,e.getMessage()));
			throw new AppRuntimeException(IMsg._999);
		}
	}

	@Override
	public Session getSession(){
		return getEntityManager().getSession();
	}
	
	@Override
	public Transaction getTransient() {
		return getEntityManager().getTransaction();
	}

	@Override
	public void closeConnection() throws AppException {
		try{
			getEntityManager().getSession().closeConnection();
		}catch(Exception e){
			throw new AppException(e.getMessage());
		}
	}
	
}