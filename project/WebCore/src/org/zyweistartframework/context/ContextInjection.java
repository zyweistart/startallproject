package org.zyweistartframework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Resource;
import org.zyweistartframework.exception.DataSourceError;
import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.PersistenceConfig;
import org.zyweistartframework.persistence.annotation.PersistenceContext;
import org.zyweistartframework.persistence.factory.mssqlserver.MSSQLServerDataContext;
import org.zyweistartframework.persistence.factory.mysql.MySQLDataContext;
import org.zyweistartframework.persistence.factory.oracle.OracleDataContext;
import org.zyweistartframework.utils.StackTraceInfo;

/**
 * 容器参数注入
 * @author Start
 */
public final class ContextInjection {
	
	private final static Log log=LogFactory.getLog(ContextInjection.class);
	
	private EntityManager entityManager;
	
	private Map<String,Object> resourceInjection=new HashMap<String,Object>();
	/**
	 * 缓存Resource注解的字段
	 */
	private static Map<Class<?>,Map<String,Field>> cacheResourceField=new ConcurrentHashMap<Class<?>,Map<String,Field>>();
	/**
	 * 缓存PersistenceContext注解的容器字段
	 */
	private static Map<Class<?>,Set<Field>> cachePersistenceContextField=new ConcurrentHashMap<Class<?>,Set<Field>>();
	/**
	 * 根据配置自动创建数据库容器
	 */
	public EntityManager getEntityManager() {
		if(entityManager==null){
			if(ConfigConstant.DIALECT.equals(PersistenceConfig.ORACLEDIALECT)){
				//Oracle
				entityManager=new OracleDataContext();
			}else if(ConfigConstant.DIALECT.equals(PersistenceConfig.MYSQLDIALECT)){
				//MySQL
				entityManager=new MySQLDataContext();
			}else if(ConfigConstant.DIALECT.equals(PersistenceConfig.MSSQLSERVERDIALECT)){
				//MSSQLServer
				entityManager=new MSSQLServerDataContext();
			}else{
				throw new DataSourceError(Message.getMessage(Message.PM_5006,ConfigConstant.DIALECT));
			}
		}
		return entityManager;
	}
	
	private Object getResourceInjectionValue(String resourceName){
		Object tarObj=resourceInjection.get(resourceName);
		if(tarObj==null){
			Class<?> prototype=GlobalEntityContext.getInjections().get(resourceName);
			if(prototype!=null){
				tarObj= createInstance(prototype);
				resourceInjection.put(resourceName, tarObj);
			}else{
				throw new NullPointerException(Message.getMessage(Message.PM_1003, resourceName));
			}
		}
		return tarObj;
	}
	
	public Object createInstance(Class<?> prototype){
		Object instance = null;
		Constructor<?>[] constructors = prototype.getConstructors();
		for (Constructor<?> constructor : constructors) {
			Annotation[][] annotations = constructor.getParameterAnnotations();
			for(Annotation[] anns:annotations){
				for (Annotation annotation : anns) {
					if (annotation.annotationType().equals(Qualifier.class)) {
						try {
							instance=constructor.newInstance(getResourceInjectionValue(((Qualifier)annotation).value()));
						} catch (Exception e) {
							log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
		}
		//如果构造函数未注册则创造一个实例
		if (instance == null) {
			try {
				//创建的对象必须要有一个默认的构造函数
				instance = prototype.newInstance();
			} catch (Exception e) {
				log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
				e.printStackTrace();
			}
		}
		Map<String,Field> cacheField=cacheResourceField.get(prototype);
		Set<Field> cacheEntityContextField=cachePersistenceContextField.get(prototype);
		if(cacheField==null&&cacheEntityContextField==null){
			//创建缓存
			cacheField=new HashMap<String,Field>();
			//创建缓存容器
			cacheEntityContextField=new HashSet<Field>();
			//拷贝一份字符码
			Class<?> cloneProperty=prototype;
			while (true) {
				if (cloneProperty != null) {
					Field[] fields = cloneProperty.getDeclaredFields();
					for (Field field : fields) {
						Resource resource=field.getAnnotation(Resource.class);
						if (resource!=null) {
							//注入的资源名称
							String resourceName=resource.value().isEmpty()?field.getName():resource.value();
							field.setAccessible(true);
							try {
								field.set(instance,getResourceInjectionValue(resourceName));
							} catch (Exception e) {
								log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
								e.printStackTrace();
							}
							//加入缓存
							cacheField.put(resourceName, field);
						}else if (field.getAnnotation(PersistenceContext.class)!=null) {
							field.setAccessible(true);
							try {
								field.set(instance,getEntityManager());
							} catch (Exception e) {
								log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
								e.printStackTrace();
							}
							//加入缓存
							cacheEntityContextField.add(field);
						}
					}
				} else {
					break;
				}
				cloneProperty = cloneProperty.getSuperclass();
			}
			cacheResourceField.put(prototype,cacheField);
			cachePersistenceContextField.put(prototype, cacheEntityContextField);
		}else{
			//字段注入
			for(String key:cacheField.keySet()){
				Field field=cacheField.get(key);
				try {
					//由于在存入缓存时已经调用field.setAccessible(true);故在此不需要重复调用
					field.set(instance, getResourceInjectionValue(key));
				} catch (Exception e) {
					log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
					e.printStackTrace();
				}
			}
			//注入实体容器对象
			for(Field field:cacheEntityContextField){
				try {
					//由于在存入缓存时已经调用field.setAccessible(true);故在此不需要重复调用
					field.set(instance, getEntityManager());
				} catch (Exception e) {
					log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return instance;
	}
	/**
	 * 用于WebJSP页面中调用并创建容器对象
	 */
	public Object createWebApplicationContext(String action){
		Class<?> prototype=GlobalEntityContext.getActions().get(action);
		if(prototype!=null){
			return createInstance(prototype);
		}else{
			String message=Message.getMessage(Message.PM_1003, action);
			log.error(StackTraceInfo.getTraceInfo() + message);
			throw new NullPointerException(message);
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		//析构实体管理器关闭数据库连接
		if(entityManager!=null){
			entityManager.finalize();
		}
		super.finalize();
	}
	
}