package org.zyweistartframework.persistence;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.zyweistartframework.collections.HashSetString;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.persistence.annotation.Temporal;

public final  class CacheSQL {
	/**
	 * 缓存rSet对象中的列名
	 */
	private static Map<Class<?>,HashSetString> cacheColumnNamesByResultSet=new ConcurrentHashMap<Class<?>,HashSetString>();
	/**
	 * 根据rSet对象获取查询的字段名集合
	 */
	public static HashSetString getColumnNamesByResultSet(ResultSet rSet,Class<?> prototype) throws SQLException{
		HashSetString columnSetString=cacheColumnNamesByResultSet.get(prototype);
		if(columnSetString==null){
			columnSetString=new HashSetString();
			ResultSetMetaData resultSetMetaData=rSet.getMetaData();
			for(int i=1;i<=resultSetMetaData.getColumnCount();i++){
				columnSetString.add(resultSetMetaData.getColumnName(i));
			}
			cacheColumnNamesByResultSet.put(prototype, columnSetString);
		}
		return columnSetString;
	}
	
	private final static Map<Class<?>,String> loadSQLCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 加载语句缓存
	 * <pre>
	 * SELECT FIELD1,FIELD2 FROM TABLE1 WHERE PKID=?
	 * </pre>
	 */
	public static String getLoadCacheSQL(EntityMember entityMember){
		String query=loadSQLCache.get(entityMember.getPrototype());
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
    		sSql.append(Constants.SELECT);
    		sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(entityMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(entityMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
    		sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(entityMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			query=sSql.toString();
			loadSQLCache.put(entityMember.getPrototype(),query);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,Map<String,String>> loadSingleFieldCacheSQL=new ConcurrentHashMap<Class<?>,Map<String,String>>();
	/**
	 * 对单列查询字段的语句缓存
	 * <pre>
	 * SELECT FIELD1 FROM TABLE1 WHERE PKID=? 
	 * </pre>
	 */
	public static String getLoadSingleFieldCacheSQL(EntityMember entityMember,PropertyMember propertyMember){
		String query=null;
		Map<String,String> cacheMap=loadSingleFieldCacheSQL.get(entityMember.getPrototype());
		if(cacheMap==null){
			cacheMap=new HashMap<String,String>();
		}else{
			query=cacheMap.get(propertyMember.getFieldName());
		}
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
    		sSql.append(Constants.SELECT);
    		sSql.append(Constants.SPACE);
    		if(propertyMember.isDateflag()){
    			Temporal temporal=(Temporal)propertyMember.getCurrentFieldAnnotation();
         		if(ConfigConstant.DIALECT.equals(PersistenceConfig.ORACLEDIALECT)){
         			//Oracle日期转字符串函数TO_CHAR
         			sSql.append("TO_CHAR");
         			sSql.append(Constants.LEFTBRACKETS);
             		sSql.append(PersistenceConfig.LEFTSEPARATED);
             		sSql.append(propertyMember.getFieldName());
             		sSql.append(PersistenceConfig.RIGHTSEPARATED);
             		sSql.append(Constants.COMMA);
             		sSql.append("'");
	        		if(temporal.format().isEmpty()){
	         			//调用默认格式
	        			sSql.append(PersistenceConfig.DATETIMEFORMAT.get(temporal.value()));
	     			}else{
	     				sSql.append(temporal.format());
	     			}
	        		sSql.append("'");
	        		sSql.append(Constants.RIGHTSBRACKETS);
	        		sSql.append(Constants.SPACE);
	        		sSql.append(Constants.AS);
	        		sSql.append(Constants.SPACE);
         		}
    		}
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(propertyMember.getFieldName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(entityMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
    		sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(entityMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			
			query=sSql.toString();
			cacheMap.put(propertyMember.getFieldName(),query);
			loadSingleFieldCacheSQL.put(entityMember.getPrototype(),cacheMap);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,String> loadSQLMOneToOneCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 主一对一
	 * <pre>
	 * SELECT FIELD1,FIELD2,FIELD3 FROM TABLE1 WHERE PKID=?
	 * </pre>
	 */
	public static String getMOneToOneCacheSQL(EntityMember tarMember){
		String query=loadSQLMOneToOneCache.get(tarMember.getPrototype());
		if(query==null){
			StringBuilder sSql=new StringBuilder();
    		sSql.append(Constants.SELECT);
			sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(tarMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(tarMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
    		sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(tarMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			query=sSql.toString();
			loadSQLMOneToOneCache.put(tarMember.getPrototype(), query);
    	}
		return query;
	}
	
	private final static Map<Class<?>,String> loadSQLOneToOneCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 一对一
	 * <pre>
	 * SELECT FIELD1,FIELD2,FIELD3 FROM TABLE1 WHERE ONETOONEFIELDNAME=?
	 * </pre>
	 */
	public static String getOneToOneCacheSQL(EntityMember tarMember,PropertyMember propertyMember){
		String query=loadSQLOneToOneCache.get(tarMember.getPrototype());
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
    		sSql.append(Constants.SELECT);
			sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(tarMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(tarMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
    		sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(propertyMember.getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			query=sSql.toString();
			loadSQLOneToOneCache.put(tarMember.getPrototype(), query);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,String> loadSQLManyToOneCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 多对一
	 * <pre>
	 * SELECT FIELD1,FIELD2,FIELD3 FROM TABLE1 WHERE PKID=?
	 * </pre>
	 */
	public static String getManyToOneCacheSQL(EntityMember tarMember){
		String query=loadSQLManyToOneCache.get(tarMember.getPrototype());
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
			sSql.append(Constants.SELECT);
			sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(tarMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(tarMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
    		sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(tarMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			query=sSql.toString();
			loadSQLManyToOneCache.put(tarMember.getPrototype(), query);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,String> loadSQLManyToOneCacheLazy=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 
	 */
	public static String getManyToOneCacheSQLLazy(EntityMember entityMember,PropertyMember propertyMember,EntityMember tarMember){
		String query=loadSQLManyToOneCacheLazy.get(tarMember.getPrototype());
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
    		sSql.append(Constants.SELECT);
    		sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(tarMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(tarMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(entityMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.LEFTBRACKETS);
			sSql.append(Constants.SELECT);
			sSql.append(Constants.SPACE);
			sSql.append(propertyMember.getFieldName());
			sSql.append(Constants.SPACE);
			sSql.append(Constants.FROM);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(entityMember.getTableName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.SPACE);
			sSql.append(Constants.WHERE);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(entityMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			sSql.append(Constants.RIGHTSBRACKETS);
			query=sSql.toString();
			loadSQLManyToOneCacheLazy.put(tarMember.getPrototype(), query);
    	}
    	return query;
	}
	
	
	private final static Map<Class<?>,String> loadSQLOneToManyCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 一对多
	 * <pre>
	 * SELECT FIELD1,FIELD2,FIELD3 FROM TABLE1 WHERE FIELDNAME=?
	 * </pre>
	 */
	public static String getOneToManyCacheSQL(EntityMember tarMember,PropertyMember propertyMember){
		String query=loadSQLOneToManyCache.get(tarMember.getPrototype());
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
			sSql.append(Constants.SELECT);
			sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(tarMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(tarMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
    		sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(propertyMember.getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			query=sSql.toString();
			loadSQLOneToManyCache.put(tarMember.getPrototype(),query);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,String> loadSQLManyToManyCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 多对多
	 * <pre>
	 * SELECT FIELDL1,FIELDL2,FIELDL3 FROM TABLE1 WHERE PKID IN(SELECT FKID FROM JOINTABLE1 WHERE FIELDNAME=?)
	 * </pre>
	 */
	public static String getManyToManyCacheSQL(EntityMember tarMember,PropertyMember propertyMember){
		String query=loadSQLManyToManyCache.get(tarMember.getPrototype());
    	if(query==null){
    		StringBuilder sSql=new StringBuilder();
    		sSql.append(Constants.SELECT);
    		sSql.append(Constants.SPACE);
    		sSql.append(EntityFactory.getInstance().builderMainFieldsSQLQuery(tarMember));
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.FROM);
    		sSql.append(Constants.SPACE);
    		sSql.append(PersistenceConfig.LEFTSEPARATED);
    		sSql.append(tarMember.getTableName());
    		sSql.append(PersistenceConfig.RIGHTSEPARATED);
    		sSql.append(Constants.SPACE);
    		sSql.append(Constants.WHERE);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(tarMember.getPKPropertyMember().getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.SPACE);
			sSql.append(Constants.IN);
			sSql.append(Constants.LEFTBRACKETS);
			sSql.append(Constants.SELECT);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(propertyMember.getForeignKeysFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.SPACE);
			sSql.append(Constants.FROM);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(propertyMember.getJoinTableName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.SPACE);
			sSql.append(Constants.WHERE);
			sSql.append(Constants.SPACE);
			sSql.append(PersistenceConfig.LEFTSEPARATED);
			sSql.append(propertyMember.getFieldName());
			sSql.append(PersistenceConfig.RIGHTSEPARATED);
			sSql.append(Constants.EQ);
			sSql.append(Constants.KEY);
			sSql.append(Constants.RIGHTSBRACKETS);
			query=sSql.toString();
			loadSQLManyToManyCache.put(tarMember.getPrototype(),query);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,String> deleteSQLCache=new ConcurrentHashMap<Class<?>,String>();
	/**
	 * 删除语句缓存
	 * <pre>
	 * DELETE FROM TABLE1 WHERE PKID=?
	 * </pre>
	 */
	public static String getDeleteCacheSQL(EntityMember entityMember){
		String query=deleteSQLCache.get(entityMember.getPrototype());
		if(query==null){
			//删除对象
			StringBuilder dSql=new StringBuilder();
			dSql.append(Constants.DELETE);
			dSql.append(Constants.SPACE);
			dSql.append(Constants.FROM);
			dSql.append(Constants.SPACE);
			dSql.append(PersistenceConfig.LEFTSEPARATED);
			dSql.append(entityMember.getTableName());
			dSql.append(PersistenceConfig.RIGHTSEPARATED);
			dSql.append(Constants.SPACE);
			dSql.append(Constants.WHERE);
			dSql.append(Constants.SPACE);
			dSql.append(PersistenceConfig.LEFTSEPARATED);
			dSql.append(entityMember.getPKPropertyMember().getFieldName());
			dSql.append(PersistenceConfig.RIGHTSEPARATED);
			dSql.append(Constants.EQ);
			dSql.append(Constants.KEY);
			query=dSql.toString();
			deleteSQLCache.put(entityMember.getPrototype(),query);
		}
		return query;
	}
	
	private final static Map<Class<?>,Map<String,String>> pmManyToManyDeleteCacheSQL=new ConcurrentHashMap<Class<?>,Map<String,String>>();
	/**
	 * 多对多关系删除
	 */
	public static String getPMManyToManyDeleteCacheSQL(EntityMember entityMember,PropertyMember propertyMember){
		String query=null;
		Map<String,String> cacheMap=pmManyToManyDeleteCacheSQL.get(entityMember.getPrototype());
		if(cacheMap==null){
			cacheMap=new HashMap<String,String>();
		}else{
			query=cacheMap.get(propertyMember.getFieldName());
		}
    	if(query==null){
    		StringBuilder dSql=new StringBuilder();
			dSql.append(Constants.DELETE);
			dSql.append(Constants.SPACE);
			dSql.append(Constants.FROM);
			dSql.append(Constants.SPACE);
			dSql.append(PersistenceConfig.LEFTSEPARATED);
			dSql.append(propertyMember.getJoinTableName());
			dSql.append(PersistenceConfig.RIGHTSEPARATED);
			dSql.append(Constants.SPACE);
			dSql.append(Constants.WHERE);
			dSql.append(Constants.SPACE);
			dSql.append(PersistenceConfig.LEFTSEPARATED);
			dSql.append(propertyMember.getFieldName());
			dSql.append(PersistenceConfig.RIGHTSEPARATED);
			dSql.append(Constants.EQ);
			dSql.append(Constants.KEY);
			query=dSql.toString();
			
			cacheMap.put(propertyMember.getFieldName(),query);
			pmManyToManyDeleteCacheSQL.put(entityMember.getPrototype(),cacheMap);
    	}
    	return query;
	}
	
	private final static Map<Class<?>,Map<String,String>> pmManyToManyInsertCacheSQL=new ConcurrentHashMap<Class<?>,Map<String,String>>();
	/**
	 * 多对多关系添加
	 */
	public static String getPMManyToManyInsertCacheSQL(EntityMember entityMember,PropertyMember propertyMember){
		String query=null;
		Map<String,String> cacheMap=pmManyToManyInsertCacheSQL.get(entityMember.getPrototype());
		if(cacheMap==null){
			cacheMap=new HashMap<String,String>();
		}else{
			query=cacheMap.get(propertyMember.getFieldName());
		}
    	if(query==null){
    		StringBuilder iSql=new StringBuilder();
			iSql.append(Constants.INSERT);
			iSql.append(Constants.SPACE);
			iSql.append(Constants.INTO);
			iSql.append(Constants.SPACE);
			iSql.append(PersistenceConfig.LEFTSEPARATED);
			iSql.append(propertyMember.getJoinTableName());
			iSql.append(PersistenceConfig.RIGHTSEPARATED);
			iSql.append(Constants.LEFTBRACKETS);
			iSql.append(PersistenceConfig.LEFTSEPARATED);
			iSql.append(propertyMember.getFieldName());
			iSql.append(PersistenceConfig.RIGHTSEPARATED);
			iSql.append(Constants.COMMA);
			iSql.append(PersistenceConfig.LEFTSEPARATED);
			iSql.append(propertyMember.getForeignKeysFieldName() );
			iSql.append(PersistenceConfig.RIGHTSEPARATED);
			iSql.append(Constants.RIGHTSBRACKETS);
			iSql.append(Constants.SPACE);
			iSql.append(Constants.VALUES);
			iSql.append(Constants.LEFTBRACKETS);
			iSql.append(Constants.KEY);
			iSql.append(Constants.COMMA);
			iSql.append(Constants.KEY);
			iSql.append(Constants.RIGHTSBRACKETS);
			query=iSql.toString();
			
			cacheMap.put(propertyMember.getFieldName(),query);
			pmManyToManyInsertCacheSQL.put(entityMember.getPrototype(),cacheMap);
    	}
    	return query;
	}
}
