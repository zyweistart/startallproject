package com.hightern.kernel.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hightern.kernel.dao.impl.BaseDaoImpl;

/**
 * JPA辅助类
 * 
 * @author Stone
 */
public class JPAHelper {

	/**
	 * 根据基础JPQL查询语句和查询对象构建Map
	 * 
	 * @param jpql
	 * @param object
	 * @return {@link Map}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map buildReflectJpql(String jpql, Object object) {
		final Map jpqlMap = new HashMap();
		if (null == jpql) { return null; }
		if (null == object) {
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
		final Method[] methods = object.getClass().getDeclaredMethods();
		for (final Method element : methods) {
			if (element.getName().indexOf("get") == 0) {
				final String field = element.getName().substring(3, 4).toLowerCase() + element.getName().substring(4);
				Object o = new Object();
				try {
					o = element.invoke(object);
				} catch (final IllegalAccessException ex) {
					Logger.getLogger(BaseDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
				} catch (final IllegalArgumentException ex) {
					Logger.getLogger(BaseDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
				} catch (final InvocationTargetException ex) {
					Logger.getLogger(BaseDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
				}
				if (NumberUtil.isNumber(o)) {
					if (!"-1".equals(o + "".trim()) && !"0.0".equals(o + "".trim())) {
						sb.append(" and o." + field + "= ? ");
						params.add(o);
					}
				} else if (NumberUtil.isDate(o)) {
					if ((null != o) && !"".equals(o + "".trim())) {
						sb.append(" and o." + field + " = ? ");
						params.add(o);
					}
				} else {
					if ((null != o) && !"".equals(o + "".trim())) {
						sb.append(" and o." + field + " like ? ");
						params.add("%" + o + "%");
					}
				}
			}
		}
		jpql += sb.toString();
		jpql += " order by o.id desc";
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
	@SuppressWarnings("rawtypes")
	public static String getJpql(Map map) {
		if (null == map) { return null; }
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
	@SuppressWarnings("rawtypes")
	public static Object[] getParams(Map map) {
		if (null == map) { return null; }
		List list = new ArrayList();
		for (final Iterator it = map.keySet().iterator(); it.hasNext();) {
			final Object key = it.next();
			if (null == map.get(key)) { return null; }
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
	public static boolean validJpql(String jpql) {
		if (jpql.toLowerCase().indexOf("select") < 0) { return false; }
		if (jpql.toLowerCase().indexOf("from") < 0) { return false; }
		return true;
	}
}
