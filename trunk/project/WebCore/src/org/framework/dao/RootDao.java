package org.framework.dao;

import java.io.Serializable;
import java.util.List;

import org.framework.entity.Root;
import org.framework.exception.AppException;
import org.zyweistartframework.persistence.ds.Session;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.persistence.factory.oracle.RowSet;

public abstract interface RootDao<T extends Root,PK extends Serializable> {
	/**
	 * 保存或更新实体有对应的关系也会级联保存或更新
	 * <pre>
	 * true: 持久化保存对象
	 * false:持久化更新对象
	 *  </pre>
	 */
	void save(T entity,Boolean flag);
	/**
	 * 删除对象如果有对应的关系也会级联删除
	 */
	void remove(T entity);
	/**
	 * <pre>
	 * 加载对象
	 * @param flag
	 * true: 正常加载
	 * false:延迟加载(若开启LAZYLOADMODE=true则该方法无实际意义)
	 * </pre>
	 */
	T load(PK id,Boolean flag);
	/**
	 * <pre>
	 * 执行NSQL语句
	 * @param firstResult
	 * 第一条记录的位置
	 * @param maxResult
	 * 一共需要获取的记录数
	 * </pre>
	 */
	List<T> getResultList(String nSql,Integer firstResult,Integer maxResult,Object... params);
	/**
	 * 执行原生SQL语句返回实体对象
	 */
	List<T> createNativeSQL(String nativeSQL,Object...params);
	/**
	 * <pre>
	 * 返回聚合值，只为单条数据
	 * count,max,min,sum,avg等等函数
	 * 返回单行单列数据
	 * </pre>
	 */
	int getSingleResult(String nSql,Object...params);
	/**
	 * 执行原生SQL查询语句返回PreparedStatement对象
	 */
	RowSet executeQuery(String executeQuery,Object... params);
	/**
	 * 原生SQL语句返回的聚合查询
	 * <pre>
	 * 返回聚合值，只为单条数据
	 * count,max,min,sum,avg等等函数
	 * 返回单行单列数据
	 * </pre>
	 */
	int polymerizationQuery(String executeQuery,Object... params);
	/**
	 * 执行原生CUD语句
	 */
	int executeUpdate(String executeUpdate,Object... params);
	/**
	 * 获取数据库会话对象
	 */
	Session getSession();
	/**
	 * 获取事务对象
	 */
	Transaction getTransient();
	/**
	 * <pre>
	 * 关闭数据库连接对象
	 * (Action中一般不直接调用该方法)
	 * </pre>
	 */
	void closeConnection() throws AppException;
}
