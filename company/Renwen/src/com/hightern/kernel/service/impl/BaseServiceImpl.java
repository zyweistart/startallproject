/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.service.impl;

import com.hightern.kernel.action.BaseAction;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hightern.kernel.dao.BaseDao;
import com.hightern.kernel.exception.ServiceExceptions;
import com.hightern.kernel.model.BaseModel;
import com.hightern.kernel.service.BaseService;
import com.hightern.kernel.util.NumberUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

@Transactional
public class BaseServiceImpl<T extends BaseModel, PK extends Serializable>
        implements BaseService<T, PK> {

    Logger logger = Logger.getLogger(this.getClass());
    private final BaseDao<T, PK> baseDao;

    public BaseServiceImpl(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    public T findById(PK id) throws ServiceExceptions {
        return baseDao.findById(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> paginated(T t) {
        final Integer count = baseDao.getCount(t);
        t.setTotalRows(count);
        return baseDao.paginated(t);
    }

    
    @Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection() throws ServiceExceptions {
		return this.baseDao.getCollection();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(T entity) throws ServiceExceptions {
		return this.baseDao.getCollection(entity);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(T entity, boolean pageFlag)
			throws ServiceExceptions {
		if (pageFlag) {
			final Integer count = this.baseDao.getCount(null, entity);
			entity.setTotalRows(count);
		}
		return this.baseDao.getCollection(entity, pageFlag);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(T entity, String[] attributes) {
		return this.baseDao.getCollection(entity, attributes);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(T entity, String[] attributes,
			boolean pageFlag) {
		if (pageFlag) {
			final Integer count = this.baseDao.getCount(null, entity);
			entity.setTotalRows(count);
		}
		return this.baseDao.getCollection(entity, attributes, pageFlag);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(String jpql, T entity,
			boolean pageFlag) throws ServiceExceptions {
		if (pageFlag) {
			final Integer count = this.baseDao.getCount(jpql, entity);
			entity.setTotalRows(count);
		}
		return this.baseDao.getCollection(jpql, entity, pageFlag);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(String jpql, T entity,
			String[] attributes, boolean pageFlag) throws ServiceExceptions {
		if (pageFlag) {
			final Integer count = this.baseDao.getCount(jpql, entity);
			entity.setTotalRows(count);
		}
		return this.baseDao.getCollection(jpql, entity, attributes, pageFlag);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(String jpql, Object[] params)
			throws ServiceExceptions {
		return this.baseDao.getCollection(jpql, params);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getCollection(String jpql, String[] attributes,
			Object[] params) throws ServiceExceptions {
		return this.baseDao.getCollection(jpql, attributes, params);
	}

    
    
    
    @SuppressWarnings("unchecked")
    public boolean remove(List<PK> ids) throws ServiceExceptions {
        try {
            for (final Object o : ids) {
                baseDao.remove((PK) o);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean remove(PK id) throws ServiceExceptions {
        try {
            baseDao.remove(id);
        } catch (final Exception e) {
            logger.debug(e);
            return false;
        }
        return true;
    }

    public boolean remove(T entity) throws ServiceExceptions {
        try {
            baseDao.remove(entity);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public T save(T entity) throws ServiceExceptions {
        T t = null;
        try {
            t = baseDao.save(entity);
        } catch (final Exception e) {
            logger.debug(e);
        }
        return t;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> queryForObject(String jpql, Object[] params) {
        List<T> datas = null;
        try {
            datas = baseDao.queryForObject(jpql, params);
        } catch (Exception e) {
            logger.error(e);
        }
        return datas;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<T> findAll() {
        List<T> datas = null;
        try {
            datas = baseDao.findAll();
        } catch (Exception e) {
            logger.error(e);
        }
        return datas;
    }

    /**
     * 利用反射机制构建jpql
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map buildReflectJpql(String jpql, T t) {
        final Map jpqlMap = new HashMap();
        if (null == jpql) {
            return null;
        }
        if (null == t) {
            jpqlMap.put((jpql + " order by o.id ").trim(), null);
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
                final String field = element.getName().substring(3, 4).toLowerCase()
                        + element.getName().substring(4);
                Object o = new Object();
                try {
                    o = element.invoke(t);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(
                            BaseAction.class.getName()).log(Level.SEVERE, null,
                            ex);
                } catch (IllegalArgumentException ex) {
                    java.util.logging.Logger.getLogger(
                            BaseAction.class.getName()).log(Level.SEVERE, null,
                            ex);
                } catch (InvocationTargetException ex) {
                    java.util.logging.Logger.getLogger(
                            BaseAction.class.getName()).log(Level.SEVERE, null,
                            ex);
                }
                if (NumberUtil.isNumber(o)) {
                    if (!"0".equals(o + "".trim())
                            && !"0.0".equals(o + "".trim())) {
                        sb.append(" and o." + field + "= ? ");
                        params.add(o);
                    }
                } else if (NumberUtil.isDate(o)) {
                    if (null != o && !"".equals(o + "".trim())) {
                        sb.append(" and o." + field + "= ? ");
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
        jpql += " order by o.id ";
        jpql.trim();
        jpqlMap.put(jpql, params);
        return jpqlMap;
    }

    /**
     * 取得jpql语句
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"rawtypes" })
    public String getJpql(Map map) {
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
     * @return
     */
    @SuppressWarnings(value = "rawtypes")
    public Object[] getParams(Map map) {
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
	 * 首页 分页 by zxb
	 * 
	 * @param jpql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<T> queryByPage(String jpql,T t){
		t.setPageNo(t.getPageNo() > 0 ? t
				.getPageNo() : 1);
		List ts = baseDao.queryForObject(jpql, null);
		if (ts.size() % t.getPageSize() == 0) {
			t.setPageCount(ts.size() == 0 ? 1 : ts.size()/t.getPageSize());
		} else {
			t.setPageCount(ts.size()/ t.getPageSize() + 1);
		}
		if (t.getPageNo() >= t.getPageCount()) {
			t.setPageNo(t.getPageCount());
		}
		
		return baseDao.queryByPage(jpql, t.getPageNo(), t.getPageSize());
	}
	
	  
    /**
	 * 首页 分页 by zxb
	 * 复写
	 * @param jpql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<T> queryByPagetwo(String jpql,T t){
		t.setPageNo(t.getPageNo() > 0 ? t
				.getPageNo() : 1);
		List ts = baseDao.queryForObject(jpql, null);
		if (ts.size() % t.getPageSize() == 0) {
			t.setPageCount(ts.size() == 0 ? 1 : ts.size()/t.getPageSize());
		} else {
			t.setPageCount(ts.size()/ t.getPageSize() + 1);
		}
		if (t.getPageNo() >= t.getPageCount()) {
			t.setPageNo(t.getPageCount());
		}
		if(ts!=null && ts.size()>0){
			t.setTotalRows(ts.size());
		}else{
			t.setTotalRows(0);
		}
		return baseDao.queryByPage(jpql, t.getPageNo(), t.getPageSize());
	}
	
}
