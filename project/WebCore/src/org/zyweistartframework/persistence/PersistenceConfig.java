package org.zyweistartframework.persistence;

import java.util.HashMap;
import java.util.Map;

import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.exception.DataSourceError;
import org.zyweistartframework.persistence.annotation.dt.TemporalType;

public final class PersistenceConfig {
	/**
	 * Oracle
	 */
	public final static String ORACLEDIALECT="OracleDialect";
	/**
	 * MySQL
	 */
	public final static String MYSQLDIALECT="MySQLDialect";
	/**
	 * MSSQLServer
	 */
	public final static String MSSQLSERVERDIALECT="MSSQLServerDialect";
	/**
	 * CGLIB生成类所包含的名称
	 */
	public final static String CGLIBCLASSNAME="$$EnhancerByCGLIB$$";
	/**
	 * 左分隔符,右分隔符?用于过滤特殊符号
	 * <pre>
	 * 例:Oracle为例
	 * SELECT T.USERNAME,T.PASSWORD FROM USER T
	 * 转成
	 * SELECT T."USERNAME",T."PASSWORD" FROM "USER" T
	 * </pre>
	 */
	public final static String LEFTSEPARATED;
	public final static String RIGHTSEPARATED;
	/**
	 * 日期时间格式
	 */
	public final static Map<TemporalType,String> DATETIMEFORMAT=new HashMap<TemporalType,String>();
	
	static{
		if(ORACLEDIALECT.equals(ConfigConstant.DIALECT)){
			//字段过滤
			LEFTSEPARATED=ConfigConstant.ADDFILTER?"\"":"";
			RIGHTSEPARATED=ConfigConstant.ADDFILTER?"\"":"";
			//日期时间
			DATETIMEFORMAT.put(TemporalType.DATE, "YYYY-MM-DD");
			DATETIMEFORMAT.put(TemporalType.TIME, "HH24:MI:SS");
			DATETIMEFORMAT.put(TemporalType.TIMESTAMP, "YYYY-MM-DD HH24:MI:SS");
		}else if(MYSQLDIALECT.equals(ConfigConstant.DIALECT)){
			//字段过滤
			LEFTSEPARATED=ConfigConstant.ADDFILTER?"`":"";
			RIGHTSEPARATED=ConfigConstant.ADDFILTER?"`":"";
			//日期时间
			DATETIMEFORMAT.put(TemporalType.DATE, "%Y-%m-%d");
			DATETIMEFORMAT.put(TemporalType.TIME, "%H:%i:%s");
			DATETIMEFORMAT.put(TemporalType.TIMESTAMP, "%Y-%m-%d %H:%i:%s");
		}else if(MSSQLSERVERDIALECT.equals(ConfigConstant.DIALECT)){
			//字段过滤
			LEFTSEPARATED=ConfigConstant.ADDFILTER?"[":"";
			RIGHTSEPARATED=ConfigConstant.ADDFILTER?"]":"";
			//日期时间
			DATETIMEFORMAT.put(TemporalType.DATE, "yyyy-mm-dd");
			DATETIMEFORMAT.put(TemporalType.TIME, "hh:mm:ss");
			DATETIMEFORMAT.put(TemporalType.TIMESTAMP, "yyyy-mm-dd hh:mm:ss");
		}else{
	 		throw new DataSourceError(Message.getMessage(Message.PM_5006,ConfigConstant.DIALECT));
		}
	}
	
}
