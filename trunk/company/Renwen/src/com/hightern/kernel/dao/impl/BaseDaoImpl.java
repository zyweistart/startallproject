/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 * 
 */
package com.hightern.kernel.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.hightern.kernel.dao.BaseDao;
import com.hightern.kernel.exception.DaoExceptions;
import com.hightern.kernel.model.BaseModel;
import com.hightern.kernel.util.JPAHelper;
import com.hightern.kernel.util.NumberUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

/**
 * 基础持久层处理类
 * @author Stone
 * @param <T>
 * @param <PK>
 */
public class BaseDaoImpl<T extends BaseModel, PK extends Serializable>
        implements BaseDao<T, PK> {

    //实体管理
    @PersistenceContext
    private EntityManager entityManager;
    //实体对象
    private Class<T> prototype;
    //注解名称
    private String entityName;

    /**
     * 默认构造函数
     */
    public BaseDaoImpl() {
    }

    /**
     * 默认构造函数
     * @param prototype
     * @param entityName
     */
    public BaseDaoImpl(Class<T> prototype, String entityName) {
        this.prototype = prototype;
        this.entityName = entityName;
    }

    
    @Override
	@SuppressWarnings("rawtypes")
	public int getCount(String jpql, T entity) throws DaoExceptions {
		if (jpql == null || jpql.length() <= 0) {
			jpql = "select count(o) from " + this.entityName + " o";
		}
		final Map map = JPAHelper.buildReflectJpql(jpql, entity);
		final Object[] params = JPAHelper.getParams(map);
		String JPQL = JPAHelper.getJpql(map);
		JPQL = JPQL.substring(0, JPQL.lastIndexOf("order"));
		final Query query = this.entityManager.createQuery(JPQL);
		if ((null != params) && (params.length > 0)) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return Integer.parseInt(query.getSingleResult() + "");
	}
    
    
    @Override
	@SuppressWarnings("unchecked")
	public List<T> getCollection() throws DaoExceptions {
		return this.getEntityManager().createQuery("select o from " + this.entityName + " o").getResultList();
	}

	@Override
	public List<T> getCollection(T entity) throws DaoExceptions {
		return this.getCollection(entity, false);
	}

	@Override
	public List<T> getCollection(T entity, boolean pageFlag) throws DaoExceptions {
		return this.getCollection(null, entity, pageFlag);
	}

	@Override
	public List<T> getCollection(T entity, String[] attributes) throws DaoExceptions {
		return this.getCollection(entity, attributes, false);
	}

	@Override
	public List<T> getCollection(T entity, String[] attributes, boolean pageFlag) throws DaoExceptions {
		return this.getCollection(null, entity, attributes, pageFlag);
	}

	@Override
	public List<T> getCollection(String jpql, T entity, boolean pageFlag) throws DaoExceptions {
		return this.getCollection(jpql, entity, null, pageFlag);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> getCollection(String jpql, T entity, String[] attributes, boolean pageFlag) throws DaoExceptions {
		if (jpql == null || jpql.length() <= 0) {
			jpql = "select o from " + this.entityName + " o";
		}
		if (!JPAHelper.validJpql(jpql)) { throw new DaoExceptions("JPQL语句不合法,请核实!"); }
		final Map map = JPAHelper.buildReflectJpql(jpql, entity);
		String JPQL = JPAHelper.getJpql(map);
		Object[] params = JPAHelper.getParams(map);
		if (attributes != null && attributes.length > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" order by");
			for (String attribute : attributes) {
				builder.append(" o." + attribute + " ,");
			}
			String orderString = builder.substring(0, builder.lastIndexOf(",")) + " desc";
			JPQL = JPQL.substring(0, JPQL.lastIndexOf("order")) + orderString;
		}
		final Query query = entityManager.createQuery(JPQL);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
		List<T> entities = new ArrayList<T>();
		if (pageFlag) {
			entities = query.setFirstResult(entity.getEndSize()).setMaxResults(entity.getPageSize()).getResultList();
		} else {
			entities = query.getResultList();
		}
		return entities;
	}

	@Override
	public List<T> getCollection(String jpql, Object[] params) throws DaoExceptions {
		return this.getCollection(jpql, null, params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getCollection(String jpql, String[] attributes, Object[] params) throws DaoExceptions {
		if (!JPAHelper.validJpql(jpql)) { throw new DaoExceptions("JPQL语句不合法,请核实!"); }
		if (attributes != null && attributes.length > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append(" order by");
			for (String attribute : attributes) {
				builder.append(" o." + attribute + " ,");
			}
			String orderString = builder.substring(0, builder.lastIndexOf(","));
			jpql = jpql.substring(0, jpql.lastIndexOf("order")) + orderString;
		} else {
			if (jpql.indexOf("order") < 1) jpql += " order by o.id";

		}
		final Query query = entityManager.createQuery(jpql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return query.getResultList();
	}
    
    
    /**
     * 根据主建查取对象
     * @param id
     * @return T
     */
    public T findById(PK id) throws DaoExceptions{
        return entityManager.find(prototype, id);
    }

    /**
     * 查询所有
     * @return List<T>
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getEntityManager().createQuery("select o from " + entityName + " o").getResultList();
    }

    /**
     * 根据JPQL以及对象参数查询
     * @param jpql
     * @param params
     * @return List<T>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> queryForObject(String jpql, Object[] params) {
        final Query query = entityManager.createQuery(jpql);
        if (null != params && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
        List items = new ArrayList();
        items = query.getResultList();
        return items;
    }

    /**
     * 分页（根据实体对象）
     * @param t
     * @return List<T>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> paginated(T t) {
        Map map = this.buildReflectJpql("select o from " + entityName + " o", t);
        Object[] params = this.getParams(map);
        String JPQL = this.getJpql(map);
        final Query query = entityManager.createQuery(JPQL);
        if (null != params && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
        return query.setFirstResult(t.getEndSize()).setMaxResults(t.getPageSize()).getResultList();
    }

    /**
     * 取出记录条数
     * @param t
     * @return Int
     */
    @SuppressWarnings({ "rawtypes" })
    public int getCount(T t) {
        Map map = this.buildReflectJpql("select count(o) from " + entityName + " o", t);
        Object[] params = this.getParams(map);
        String JPQL = this.getJpql(map);
        JPQL = JPQL.substring(0, JPQL.lastIndexOf("order"));
        final Query query = entityManager.createQuery(JPQL);
        if (null != params && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
        return Integer.parseInt(query.getSingleResult() + "");
    }

    /**
     * 保存对象
     * @param entity
     * @return T
     */
    public T save(T entity) throws DaoExceptions{
        if (entity.getId() > 0) {
            entityManager.merge(entity);
        } else {
        	try{
        		entityManager.persist(entity);
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return entity;
    }

    /**
     * 根据主键移出
     * @param id
     */
    public void remove(PK id) throws DaoExceptions{
        final T entity = entityManager.find(prototype, id);
        entityManager.remove(entityManager.merge(entity));
    }

    /**
     * 根据对象移出
     * @param entity
     */
    public void remove(T entity) throws DaoExceptions{
        entityManager.remove(entityManager.merge(entity));
    }

    /**
     * 自动生成JPQL
     * @param jpql
     * @param t
     * @return Map
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Map buildReflectJpql(String jpql, T t) {
        final Map jpqlMap = new HashMap();
        if (null == jpql) {
            return null;
        }
        if (null == t) {
            jpqlMap.put((jpql + " order by o.id desc ").trim(), null);
            return jpqlMap;
        }
        final List params = new ArrayList();
        final StringBuilder sb = new StringBuilder();
        if (jpql.indexOf("where") > 0) {
            sb.append(" ");
        } else {
            sb.append(" where 1=1 ");
        }
        final Method[] methods = t.getClass().getDeclaredMethods();
        for (final Method element : methods) {
            if (element.getName().indexOf("get") == 0) {
                final String field = element.getName().substring(3, 4).toLowerCase() + element.getName().substring(4);
                Object o = new Object();
                try {
                    o = element.invoke(t);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(BaseDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(BaseDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(BaseDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (NumberUtil.isNumber(o)) {
                    if (!"0".equals(o + "".trim()) && !"0.0".equals(o + "".trim())) {
                        sb.append(" and o." + field + "= ? ");
                        params.add(o);
                    }
                } else if (NumberUtil.isDate(o)) {
                    if (null != o && !"".equals(o + "".trim())) {
                        sb.append(" and o." + field + " = ? ");
                        params.add(o);
                    }
                } else {
                    if (null != o && !"".equals(o + "".trim())) {
                        sb.append(" and o." + field + " like ? ");
                        params.add("%" + o + "%");
                    }
                }
            }
        }
        jpql += sb.toString();
        jpql += " order by o.id desc ";
        jpql.trim();
        jpqlMap.put(jpql, params);
        return jpqlMap;
    }

    /**
     * 取得jpql语句
     *
     * @param map
     * @return jpql
     */
    @SuppressWarnings({  "rawtypes" })
    protected String getJpql(Map map) {
        if (null == map) {
            return null;
        }
        String jpql = "";
        for (final Iterator it = map.keySet().iterator(); it.hasNext();) {
            jpql = it.next().toString();
        }
        return jpql;
    }

    /**
     * 取得jpql语句中需要的参数值
     *
     * @param map
     * @return Object[]
     */
    @SuppressWarnings({ "rawtypes" })
    protected Object[] getParams(Map map) {
        if (null == map) {
            return null;
        }
        List list = new ArrayList();
        for (final Iterator it = map.keySet().iterator(); it.hasNext();) {
            final Object key = it.next();
            if (null == map.get(key)) {
                return null;
            }
            list = (ArrayList) map.get(key);
        }
        return list.toArray();
    }

    /**
     * 检验jqpl语句的合法性
     *
     * @param jpql
     * @return boolean
     */
    public boolean validJpql(String jpql) {
        if (jpql.toLowerCase().indexOf("select") < 0) {
            return false;
        }
        if (jpql.toLowerCase().indexOf("from") < 0) {
            return false;
        }
        return true;
    }
    
    /**
	 * 首页 分页
	 * 
	 * @param jpql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByPage(String jpql, Integer pageNo, Integer pageSize) {
		return this.getEntityManager().createQuery(jpql).setFirstResult(
				pageNo * pageSize - pageSize).setMaxResults(pageSize)
				.getResultList();
	}

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setPrototype(Class<T> prototype) {
        this.prototype = prototype;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public String getEntityName() {
        return entityName;
    }

    public Class<T> getPrototype() {
        return prototype;
    }
}
