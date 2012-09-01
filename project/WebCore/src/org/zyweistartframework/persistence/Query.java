package org.zyweistartframework.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.collections.HashSetString;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.exception.HibernateException;
import org.zyweistartframework.persistence.ds.Session;
import org.zyweistartframework.persistence.proxy.ASMObjectProxyHandle;
import org.zyweistartframework.utils.StackTraceInfo;

public abstract class Query {
	
	private final static Log log=LogFactory.getLog(Query.class);
	
	public Query(EntityManager eManager,String nQuery){
		this.entityManager=eManager;
		//从缓存中直接获取
		this.convertSql=cacheNQLConvertSQL.get(nQuery);
		if(this.convertSql==null){
			this.convertSql=new NQLConvertSQL(nQuery);
			this.convertSql.analyze();
			cacheNQLConvertSQL.put(nQuery,this.convertSql);
		}
	}
	
	protected Class<?> getPrototype() {
		if(prototype==null){
			if(convertSql.getPrototypes().size()>1){
				String message=Message.getMessage(Message.PM_5010, convertSql);
				log.error(StackTraceInfo.getTraceInfo()+message);
		 		throw new HibernateException(message);
			}else{
				for(Class<?> value:convertSql.getPrototypes()){
					prototype=value;
					break;
				}
			}
		}
		return prototype;
	}
	/**
	 * NQLConvertSQL缓存
	 */
	private static final Map<String,NQLConvertSQL> cacheNQLConvertSQL=new ConcurrentHashMap<String,NQLConvertSQL>();
	/**
	 * 实体管理器
	 */
	private EntityManager entityManager;
	/**
	 * 查询所属的对象
	 */
	private Class<?> prototype;
	/**
	 * convertSql语句
	 */
	private NQLConvertSQL convertSql;
	/**
	 * 开始记录数
	 */
	private Integer firstResult=0;
	/**
	 * 最大的返回值
	 */
	private Integer maxResult;
	/**
	 * 占位符的参数
	 */
	private Map<Integer,Object> params;
	/**
	 * 设置多表查询时返回的对象
	 */
	public Query addPrototype(Class<?> prototype) {
		this.prototype = prototype;
		return this;
	}
	/**
	 * 设置分页查询时开始的记录行数
	 */
	public Query setFirstResult(Integer firstResult) {
		if(firstResult!=null){
			this.firstResult = firstResult;
		}
		return this;
	}
	/**
	 * 设置分页查询时每次需要获取的记录行数
	 */
	public Query setMaxResult(Integer maxResult) {
		if(maxResult!=null){
			this.maxResult = maxResult;
		}
		return this;
	}
	/**
	 * 设置参数
	 */
	public Query setParameter(Integer index,Object value){
		getParams().put(index, value);
		return this;
	}
	/**
	 * 返回转换后的本地原生SQL语句,如果带分页则一起转换
	 */
	protected abstract String nativeSql();
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected Integer getFirstResult() {
		return firstResult;
	}
	
	protected Integer getMaxResult() {
		return maxResult;
	}

	protected NQLConvertSQL getConvertSql() {
		return convertSql;
	}
	
	protected Map<Integer, Object> getParams() {
		if(params==null){
			params=new HashMap<Integer,Object>();
		}
		return params;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(){
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		try {
			EntityMember entityMember=EntityFactory.getInstance().getEntityMember(getPrototype());
			String nativeSql=nativeSql();
			preparedStatement=getEntityManager().getSession().getConnection().prepareStatement(nativeSql);
			for(Integer key:getParams().keySet()){
				preparedStatement.setObject(key,getParams().get(key));
			}
			resultSet =preparedStatement.executeQuery();
			HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(resultSet,getPrototype());
			List<T> entitys=new ArrayList<T>();
			while(resultSet.next()){
				Object PK=resultSet.getObject(entityMember.getPKPropertyMember().getFieldName());
				entitys.add((T) getEntityManager().getSingleEntity(resultSet, entityMember,ConfigConstant.LAZYLOADMODE?
						ASMObjectProxyHandle.create(getEntityManager(),entityMember,PK):getPrototype().newInstance(),
						null,null,null,false,PK,columnNames));
			}
			//如果全部执行正确则打印语句
			EntityFactory.getInstance().logConsole(nativeSql);
			return entitys;
		} catch (Exception e) {
	 		throw new HibernateException(Message.getMessage(Message.PM_5009,getConvertSql().getNativeNQL(),e.getMessage()));
		}finally{
			Session.closeResultSet(resultSet);
			Session.closePreparedStatement(preparedStatement);
		}
	}
	/**
	 * <pre>
	 * 返回聚合值，只为单条数据
	 * count,max,min,sum,avg等函数
	 * 返回单行单列数据
	 * <pre>
	 */
	public int getSingleResult(){
		int resultValue=0;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement=getEntityManager().getSession().getConnection().prepareStatement(getConvertSql().toString());
			for(Integer key:getParams().keySet()){
				preparedStatement.setObject(key, getParams().get(key));
			}
			resultSet =preparedStatement.executeQuery();
			if(resultSet.next()){
				//因为返回结果为单条顾用只获取第一行第一列
				resultValue=resultSet.getInt(1);
			}
			//如果全部执行正确则打印语句
			EntityFactory.getInstance().logConsole(getConvertSql().toString());
			return resultValue;
		} catch (Exception e) {
	 		throw new HibernateException(Message.getMessage(Message.PM_5009,getConvertSql().getNativeNQL(),e.getMessage()));
		}finally{
			Session.closeResultSet(resultSet);
			Session.closePreparedStatement(preparedStatement);
		}
	}
	
}