package org.framework.service;

import java.io.Serializable;
import java.util.List;

import org.framework.entity.Root;
import org.framework.exception.AppException;
import org.zyweistartframework.persistence.ds.Transaction;

public abstract interface RootService<T extends Root,PK extends Serializable> {
	/**
	 * 保存或更新实体有对应的关系也会级联保存或更新
	 * <pre>
	 * 1.持久化保存对象(主键值为null)
	 * 2.持久化更新对象(主键值不为null)
	 *  </pre>
	 */
	void save(T entity);
	/**
	 * 删除对象如果有对应的关系也会级联删除
	 */
	void remove(T entity);
	/**
	 * 加载对象
	 */
	T load(PK id);
	/**
	 * 延迟加载对象,若开启LAZYLOADMODE=true则该方法无实际意义
	 */
	T lazyLoad(PK id);
	/**
	 * 根据NSQL语句获取单条记录有且仅有一条
	 */
	T getResultListOnlySingle(String nSql, Object... params);
	/**
	 * 执行NSQL语句结果不进行分页
	 */
	List<T> getResultList(String nSql,Object... params);
	/**
	 * 执行NSQL语句分页查询
	 * @param firstResult
	 * 第一条记录的位置
	 * @param maxResult
	 * 一共需要获取的记录数
	 */
	List<T> getResultList(String nSql,Integer firstResult,Integer maxResult,Object... params);
	/**
	 * 返回聚合值，只为单条数据
	 * count,max,min,sum,avg等等函数
	 * @return 返回单行单列数据
	 */
	int getSingleResult(String nSql,Object...params) ;
	/**
	 * 根据原生SQL语句获取单条记录有且仅有一条
	 */
	T createNativeSQLOnlySingle(String nSql,Object... params);
	/**
	 * 根据原生SQL语句获取集合
	 */
	List<T> createNativeSQL(String nSql,Object... params);
	/**
	 * 获取事务对象
	 */
	Transaction getTransient();
	/**
	 * 关闭数据库连接对象
	 */
	void closeConnection() throws AppException;
}
