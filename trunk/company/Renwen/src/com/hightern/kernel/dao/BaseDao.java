/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.dao;

import java.io.Serializable;
import java.util.List;

import com.hightern.kernel.model.BaseModel;

public interface BaseDao<T extends BaseModel, PK extends Serializable> {
	
	/**
	 * 根据主键查询对象
	 * 
	 * @param id
	 * @return T
	 */
	public T findById(PK id);
	
	/**
	 * 取出记录条数
	 * 
	 * @param t
	 * @return int
	 */
	public int getCount(T t);
	
	
	/**
	 * 取得记录条数
	 * @param jpql
	 * @param entity
	 * @return {@link Integer}
	 */
	public int getCount(String jpql,T entity);
	
	
	/**
	 * 取所有对象集
	 * 
	 * @return {@link List}
	 */
	public List<T> getCollection();

	/**
	 * 根据对象取对象集;
	 * 
	 * @param entity
	 * @param pageFlag
	 * @return {@link List}
	 */
	public List<T> getCollection(T entity);

	/**
	 * 根据对象取对象集;pageFlag(分页标记)
	 * 
	 * @param entity
	 * @param pageFlag
	 * @return {@link List}
	 */
	public List<T> getCollection(T entity, boolean pageFlag);

	/**
	 * 根据对象取对象集(不分页)并按指定属性排序
	 * 
	 * @param entity
	 * @param attributes
	 * @return {@link List}
	 */
	public List<T> getCollection(T entity, String[] attributes);

	/**
	 * 根据对象取对象集并按指定属性排序;pageFlag(分页标记)
	 * 
	 * @param entity
	 * @param attributes
	 * @param pageFlag
	 * @return {@link List}
	 */
	public List<T> getCollection(T entity, String[] attributes, boolean pageFlag);

	/**
	 * 根据JPQL,实体对象取对象集;pageFlag(分页标记)
	 * 
	 * @param jpql
	 * @param entity
	 * @param pageFlag
	 * @return {@link List}
	 */
	public List<T> getCollection(String jpql, T entity, boolean pageFlag);

	/**
	 * 根据JPQL,实体对象取对象集并按指定属性排序;pageFlag(分页标记)
	 * 
	 * @param jpql
	 * @param entity
	 * @param attributes
	 * @param pageFlag
	 * @return {@link List}
	 */
	public List<T> getCollection(String jpql, T entity, String[] attributes, boolean pageFlag);

	/**
	 * 根据JPQL和参数取对象集
	 * 
	 * @param jpql
	 * @param params
	 * @return {@link List}
	 */
	public List<T> getCollection(String jpql, Object[] params);

	/**
	 * 根据JPQL和参数取对象集并按指定属性排序; (针对复合查询所用)
	 * 
	 * @param jpql
	 * @param attributes
	 * @param params
	 * @return {@link List}
	 */
	public List<T> getCollection(String jpql, String[] attributes, Object[] params);
	
	
	
	/**
	 * 根据查询语句和参数查询记录集
	 * 
	 * @param jpql
	 * @param params
	 * @return List<T>
	 */
	public List<T> queryForObject(String jpql, Object[] params);
	
	/**
	 * 查出所有记录集
	 * 
	 * @return List<T>
	 */
	public List<T> findAll();
	
	/**
	 * 分页用
	 * 
	 * @param t
	 * @return List<T>
	 */
	public List<T> paginated(T t);
	
	/**
	 * 根据主键移出对象
	 * 
	 * @param id
	 */
	public void remove(PK id);
	
	/**
	 * 根据对象移出
	 * 
	 * @param t
	 */
	public void remove(T t);
	
	/**
	 * 添加对象
	 * 
	 * @param entity
	 * @return T
	 */
	public T save(T entity);
	
	/**
	 * 首页 分页 by zxb
	 * 
	 * @param jpql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<T> queryByPage(String jpql, Integer pageNo, Integer pageSize);
}
