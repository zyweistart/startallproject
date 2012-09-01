package org.zyweistartframework.persistence.proxy;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.zyweistartframework.collections.HashSetString;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.persistence.CacheSQL;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.annotation.ManyToMany;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.OneToOne;
import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
import org.zyweistartframework.persistence.ds.Session;


public class ASMMethodInterceptor implements MethodInterceptor {

	private Object primaryKeyValue;
	
	private EntityMember entityMember;
	
	private EntityManager entityManager;
	/**
	 *  缓存已经延迟加载过的对象方法,延迟加载的对象方法只在初中调用的时候加载，再次调用则不再进行拦截
	 */
	private Map<Class<?>,Set<String>> cacheMethodInterceptor=new HashMap<Class<?>,Set<String>>();
	/**
	 * 创建动态代理
	 * @param prototype
	 * 所属的类
	 * @param entityManager
	 * 实体管理器
	 * @param PKValue
	 * 主键值	
	 * @return
	 * 返回创建好的代理对象
	 */
	public ASMMethodInterceptor(EntityManager entityManager,EntityMember entityMember,Object primaryKeyValue){
		this.primaryKeyValue=primaryKeyValue;
		this.entityMember=entityMember;
		this.entityManager=entityManager;
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,MethodProxy proxy) throws Throwable {
		Set<String> methods=cacheMethodInterceptor.get(entityMember.getPrototype());
		String methodName=method.getName();
		if(methods!=null){
			//如果方法已经调用过则直接返回结果
			if(methods.contains(methodName)){
				return proxy.invokeSuper(obj,args);
			}
		}else{
			methods=new HashSet<String>();
		}
		methods.add(methodName);
		cacheMethodInterceptor.put(entityMember.getPrototype(),methods);
		
		ResultSet rSet=null;
		PreparedStatement pstmt=null;
		for(PropertyMember propertyMember:entityMember.getPropertyMembers()){
			if(method.equals(propertyMember.getGMethod())){
				//只查询延迟加载的字段
				if(propertyMember.isLazyflag()){
					try{
						pstmt=entityManager.getSession().executeQuery(
								CacheSQL.getLoadSingleFieldCacheSQL(entityMember,propertyMember),primaryKeyValue);
						rSet=pstmt.executeQuery();
						if(rSet.next()){
							EntityFactory.getInstance().resultSetInject(rSet,propertyMember, obj);
						}
					}finally{
						Session.closeResultSet(rSet);
						Session.closePreparedStatement(pstmt);
					}
					return proxy.invokeSuper(obj,args);
				}
			}
		}
		//主一对一
		for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
			if(method.equals(propertyMember.getGMethod())){
				OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
				if(EntityFactory.getInstance().isCascade(oneToOne.cascade(), CascadeType.REFRESH)){
					if(oneToOne.fetch()==FetchType.LAZY){
						try{
							EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
							pstmt=entityManager.getSession().executeQuery(CacheSQL.getMOneToOneCacheSQL(tarMember),primaryKeyValue);
							rSet=pstmt.executeQuery();
							HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(rSet,propertyMember.getTarClass());
							if(rSet.next()){
								propertyMember.getSMethod().invoke(obj,
										entityManager.getSingleEntity(rSet, tarMember,ConfigConstant.LAZYLOADMODE?
												ASMObjectProxyHandle.create(entityManager,tarMember,primaryKeyValue):propertyMember.getTarClass().newInstance(),
										null,null, null,false,primaryKeyValue,columnNames));
							}
						}finally{
							Session.closeResultSet(rSet);
							Session.closePreparedStatement(pstmt);
						}
						return proxy.invokeSuper(obj,args);
					}
				}
			}
		}
		//一对一
		for(PropertyMember propertyMember:entityMember.getOneToOneProperyMembers()){
			if(method.equals(propertyMember.getGMethod())){
				OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
				if(EntityFactory.getInstance().isCascade(oneToOne.cascade(), CascadeType.REFRESH)){
					if(oneToOne.fetch()==FetchType.LAZY){
						try{
							EntityMember tarMember =EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
							pstmt=entityManager.getSession().executeQuery(
									CacheSQL.getOneToOneCacheSQL(tarMember,propertyMember),primaryKeyValue);
							rSet=pstmt.executeQuery();
							HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(rSet,propertyMember.getTarClass());
							if(rSet.next()){
								propertyMember.getSMethod().invoke(obj,entityManager.getSingleEntity(rSet, tarMember, 
										ConfigConstant.LAZYLOADMODE?ASMObjectProxyHandle.create(entityManager,tarMember,primaryKeyValue):
											propertyMember.getTarClass().newInstance(),null,null,null,false, primaryKeyValue,columnNames));
							}
						}finally{
							Session.closeResultSet(rSet);
							Session.closePreparedStatement(pstmt);
						}
						return proxy.invokeSuper(obj,args);
					}
				}
			}
		}
		//一对多
		for(PropertyMember propertyMember:entityMember.getOneToManyPropertyMembers()){
			if(method.equals(propertyMember.getGMethod())){
				OneToMany oneToMany=(OneToMany)propertyMember.getCurrentFieldAnnotation();
				if(EntityFactory.getInstance().isCascade(oneToMany.cascade(), CascadeType.REFRESH)){
					if(oneToMany.fetch()==FetchType.LAZY){
						try{
							EntityMember tarMember =EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
							pstmt=entityManager.getSession().executeQuery(
									CacheSQL.getOneToManyCacheSQL(tarMember, propertyMember),primaryKeyValue);
							rSet=pstmt.executeQuery();
							HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(rSet,propertyMember.getTarClass());
							Collection<Object> entitys=new ArrayList<Object>();
							while(rSet.next()){
								entitys.add(entityManager.getSingleEntity(rSet, tarMember, 
										ConfigConstant.LAZYLOADMODE?ASMObjectProxyHandle.create(entityManager,tarMember,primaryKeyValue):
											propertyMember.getTarClass().newInstance(),null,null,null,false, primaryKeyValue,columnNames));
							}
							propertyMember.getSMethod().invoke(obj, entitys);
						}finally{
							Session.closeResultSet(rSet);
							Session.closePreparedStatement(pstmt);
						}
						return proxy.invokeSuper(obj,args);
					}
				}
			}
		}
		//多对一
		for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
			if(method.equals(propertyMember.getGMethod())){
				ManyToOne manyToOne=(ManyToOne)propertyMember.getCurrentFieldAnnotation();
				if(EntityFactory.getInstance().isCascade(manyToOne.cascade(), CascadeType.REFRESH)){
					if(manyToOne.fetch()==FetchType.LAZY){
						try{
							EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
							pstmt=entityManager.getSession().executeQuery(CacheSQL.getManyToOneCacheSQLLazy(entityMember, propertyMember, tarMember),primaryKeyValue);
							rSet=pstmt.executeQuery();
							HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(rSet,propertyMember.getTarClass());
							if(rSet.next()){
								propertyMember.getSMethod().invoke(obj,
										entityManager.getSingleEntity(rSet, tarMember, ConfigConstant.LAZYLOADMODE?
												ASMObjectProxyHandle.create(entityManager,tarMember,primaryKeyValue):
											propertyMember.getTarClass().newInstance(),
										null,null, null,false,primaryKeyValue,columnNames));
							}
						}finally{
							Session.closeResultSet(rSet);
							Session.closePreparedStatement(pstmt);
						}
						return proxy.invokeSuper(obj,args);
					}
				}
			}
		}
		//多对多
		for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
			if(method.equals(propertyMember.getGMethod())){
				ManyToMany manyToMany=(ManyToMany)propertyMember.getCurrentFieldAnnotation();
				if(EntityFactory.getInstance().isCascade(manyToMany.cascade(), CascadeType.REFRESH)){
					if(manyToMany.fetch()==FetchType.LAZY){
						EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
						Collection<Object> entitys=new ArrayList<Object>();
						try{
							pstmt=entityManager.getSession().executeQuery(
									CacheSQL.getManyToManyCacheSQL(tarMember,propertyMember),primaryKeyValue);
							rSet=pstmt.executeQuery();
							HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(rSet,propertyMember.getTarClass());
							while(rSet.next()){
								Object PK=rSet.getObject(tarMember.getPKPropertyMember().getFieldName());
								entitys.add(entityManager.getSingleEntity(rSet, tarMember,ConfigConstant.LAZYLOADMODE?
										ASMObjectProxyHandle.create(entityManager,tarMember,PK):
									propertyMember.getTarClass().newInstance(),null,entitys,entityMember.getPrototype(),false, PK,columnNames));
							}
						}finally{
							Session.closeResultSet(rSet);
							Session.closePreparedStatement(pstmt);
						}
						propertyMember.getSMethod().invoke(obj, entitys);
						return proxy.invokeSuper(obj,args);
					}
				}
			}
		}
		return proxy.invokeSuper(obj,args);
	}
	
}