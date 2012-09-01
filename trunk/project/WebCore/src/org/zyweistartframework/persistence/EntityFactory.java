package org.zyweistartframework.persistence;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.GlobalEntityContext;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.exception.DataTypeException;
import org.zyweistartframework.exception.HibernateException;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.TemporalType;
import org.zyweistartframework.utils.StackTraceInfo;

/**
 * 数据库容器工厂
 */
public final class EntityFactory {
	
	private final static Log log=LogFactory.getLog(EntityFactory.class);
	
	private final static EntityFactory instance = new EntityFactory();
	
	private EntityFactory(){}
	
	public static EntityFactory getInstance() {
		return instance;
	}
	/**
	 * 打印SQL语句
	 */
	public void logConsole(String executeSql){
		log.info(executeSql);
	}
	/**
	 * 是否包含特有的级联权限
	 */
	public Boolean isCascade(CascadeType[] cascadeTypes,CascadeType cascadeType){
		for(CascadeType cascade : cascadeTypes){
			if(cascade==CascadeType.All){
				return true;
			}else if(cascade==cascadeType){
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据实体名称获取对应的EntityMember对象
	 */
	public EntityMember getEntityMemberByEntityName(String entityName){
		for(EntityMember entityMember:GlobalEntityContext.getEntitys().values()){
			if(entityMember.getEntityName().equals(entityName)){
				return entityMember;
			}
		}
 		String message=null;
		try{
			message=Message.getMessage(Message.PM_5003, entityName);
			log.error(StackTraceInfo.getTraceInfo()+message);
			throw new HibernateException(message);
		}finally{
			message=null;
		}
	}
	/**
	 * 根据类获取对应的EntityMember对象
	 */
	public EntityMember getEntityMember(Class<?> prototype){
		EntityMember entityMember=GlobalEntityContext.getEntitys().get(prototype);
		if(entityMember!=null){
			return entityMember;
		}else{
			String clsName=prototype.getSimpleName();
			//如果当前类由CGLIB自动生成则提取父类
			while(clsName.contains(PersistenceConfig.CGLIBCLASSNAME)){
				prototype=prototype.getSuperclass();
				entityMember=GlobalEntityContext.getEntitys().get(prototype);
				if(entityMember!=null){
					return entityMember;
				}
				clsName=prototype.getSimpleName();
			}
		}
		String message=null;
		try{
			message=Message.getMessage(Message.PM_5003, prototype.getSimpleName());
			log.error(StackTraceInfo.getTraceInfo()+message);
			throw new HibernateException(message);
		}finally{
			message=null;
		}
	}
	/**
	 * 生成主体的SQL查询字段语句
	 */
	String builderMainFieldsSQLQuery(EntityMember entityMember){
		Set<PropertyMember> propertyMembers=new HashSet<PropertyMember>();
		//成员
		propertyMembers.addAll(entityMember.getPropertyMembers());
		//主一对一
		propertyMembers.addAll(entityMember.getMainOneToOneProperyMembers());
		//一对一
//		propertyMembers.addAll(entityMember.getOneToOneProperyMembers());
//		//多对一
//		propertyMembers.addAll(entityMember.getManyToOnePropertyMembers());
		//一对多
		propertyMembers.addAll(entityMember.getOneToManyPropertyMembers());
		//多对多
		propertyMembers.addAll(entityMember.getManyToManyPropertyMembers());
		StringBuilder sSql=new StringBuilder();
		for(PropertyMember propertyMember:propertyMembers){
			if(!propertyMember.isLazyflag()){
				if(propertyMember.isDateflag()){
        			Temporal temporal=(Temporal)propertyMember.getCurrentFieldAnnotation();
             		if(ConfigConstant.DIALECT.equals(PersistenceConfig.ORACLEDIALECT)){
             			//Oracle日期转字符串函数TO_CHAR
             			sSql.append(Constants.TO_CHAR);
             			sSql.append(Constants.LEFTBRACKETS);
	             		sSql.append(PersistenceConfig.LEFTSEPARATED);
	             		sSql.append(propertyMember.getFieldName());
	             		sSql.append(PersistenceConfig.RIGHTSEPARATED);
	             		sSql.append(Constants.COMMA);
	             		sSql.append(Constants.QUOT);
		        		if(temporal.format().isEmpty()){
		         			//调用默认格式
		        			sSql.append(PersistenceConfig.DATETIMEFORMAT.get(temporal.value()));
		     			}else{
		     				sSql.append(temporal.format());
		     			}
		        		sSql.append(Constants.QUOT);
		        		sSql.append(Constants.RIGHTSBRACKETS);
		        		sSql.append(Constants.SPACE);
		        		sSql.append(Constants.AS);
		        		sSql.append(Constants.SPACE);
             		}
        		}
        		sSql.append(PersistenceConfig.LEFTSEPARATED);
        		sSql.append(propertyMember.getFieldName());
        		sSql.append(PersistenceConfig.RIGHTSEPARATED);
        		sSql.append(Constants.COMMA);
			}
		}
		sSql.append(PersistenceConfig.LEFTSEPARATED);
		sSql.append(entityMember.getPKPropertyMember().getFieldName());
		sSql.append(PersistenceConfig.RIGHTSEPARATED);
		return sSql.toString();
	}
	/**
	 * <pre>
	 * 插入，更新数据行时由于存在格式关系则需要进行转换
	 * 日期类型需要按指定格式进行写入以Oracle为例需要进行格式转化
	 * @param propertyMember
	 * 属性成员
	 * @param vSql
	 * 拼接语句
	 * @param value
	 * 字段值
	 * @param parameters
	 * 参数珣瞬间
	 * @param flag
	 * 调用是为Insert还是Update
	 * true  添加
	 * false 更新
	 * </pre>
	 */
	void handleInsertUpdateDataRow(PropertyMember propertyMember,StringBuilder vSql,Boolean flag){
		if(!flag){
			//更新时需加上等号
			vSql.append(Constants.EQ);
		}
		if(propertyMember.isDateflag()){
     		Temporal temporal=(Temporal)propertyMember.getCurrentFieldAnnotation();
     		if(ConfigConstant.DIALECT.equals(PersistenceConfig.ORACLEDIALECT)){
     			vSql.append(Constants.TO_DATE);
     		}else if(ConfigConstant.DIALECT.equals(PersistenceConfig.MYSQLDIALECT)){
     			if(temporal.value().equals(TemporalType.TIME)){
     				vSql.append(Constants.TIME_FORMAT);
     			}else{
     				vSql.append(Constants.DATE_FORMAT);
     			}
     		}else if(ConfigConstant.DIALECT.equals(PersistenceConfig.MSSQLSERVERDIALECT)){
     			//TODO:添加MSSQLSERVER日期时间函数
     		}
     		vSql.append(Constants.LEFTBRACKETS);
     		vSql.append(Constants.KEY);
 			vSql.append(Constants.COMMA);
 			vSql.append(Constants.QUOT);
     		if(temporal.format().isEmpty()){
     			//调用默认格式
 				vSql.append(PersistenceConfig.DATETIMEFORMAT.get(temporal.value()));
 			}else{
 				vSql.append(temporal.format());
 			}
     		vSql.append(Constants.QUOT);
 			vSql.append(Constants.RIGHTSBRACKETS);
 			vSql.append(Constants.COMMA);
     	}else{
     		vSql.append(Constants.KEY);
     		vSql.append(Constants.COMMA);
     	}
	}
	/**
	 * 添加更新数据时对数据值进行转换
	 */
	Object dataFormatVerifyConvert(Object value,EntityMember entityMember,PropertyMember propertyMember){
		//数据验证
		if(DataTypeValidation.isString.contains(propertyMember.getReturnTypeSimpleName())){
			if(propertyMember.getCurrentFieldAnnotation()!=null){
				if(propertyMember.getCurrentFieldAnnotation().annotationType().equals(Column.class)){
					Column column=(Column)propertyMember.getCurrentFieldAnnotation();
					if(!column.nullable()){
						//非空验证
						if(value==null||String.valueOf(value).isEmpty()){
							String message=Message.getMessage(Message.PM_5014,
									entityMember.getPrototype().getName(),propertyMember.getFieldName());
							log.error(StackTraceInfo.getTraceInfo()+message);
					 		throw new HibernateException(message);
						}
					}
					if(value!=null){
						//长度验证
						if(String.valueOf(value).length()>column.length()){
							String message=Message.getMessage(Message.PM_5013,
									entityMember.getPrototype().getName(),propertyMember.getFieldName(),column.length());
							log.error(StackTraceInfo.getTraceInfo()+message);
					 		throw new HibernateException(message);
						}
					}
				}else if(propertyMember.getCurrentFieldAnnotation().annotationType().equals(Temporal.class)){
					Temporal temporal=(Temporal)propertyMember.getCurrentFieldAnnotation();
					if(!temporal.nullable()){
						//非空验证
						if(value==null||String.valueOf(value).isEmpty()){
							String message=Message.getMessage(Message.PM_5014,
									entityMember.getPrototype().getName(),propertyMember.getFieldName());
							log.error(StackTraceInfo.getTraceInfo()+message);
					 		throw new HibernateException(message);
						}
					}
				}else if(propertyMember.getCurrentFieldAnnotation().annotationType().equals(Lob.class)){
					Lob lob=(Lob)propertyMember.getCurrentFieldAnnotation();
					if(!lob.nullable()){
						if(value==null||String.valueOf(value).isEmpty()){
							String message=Message.getMessage(Message.PM_5014,
									entityMember.getPrototype().getName(),propertyMember.getFieldName());
							log.error(StackTraceInfo.getTraceInfo()+message);
					 		throw new HibernateException(message);
						}
					}
				}
			}
		}
		if(DataTypeValidation.isBoolean.contains(propertyMember.getReturnTypeSimpleName())){
			if(value!=null){
				value=Boolean.parseBoolean(value.toString())?Constants.TRUE:Constants.FALSE;
			}else{
				value=Constants.FALSE;
			}
		}else if(value!=null){
			if(DataTypeValidation.isString.contains(propertyMember.getReturnTypeSimpleName())){
				if(ConfigConstant.DIALECT.equals(PersistenceConfig.ORACLEDIALECT)){
					if(propertyMember.getCurrentFieldAnnotation()!=null
							&&propertyMember.getCurrentFieldAnnotation().annotationType().equals(Lob.class)){
						int length=value.toString().length();
						if(length>=1000&&length<=2000){
							int l=2001-length;
							StringBuilder sb=new StringBuilder();
							for(int i=0;i<l;i++){
								sb.append(Constants.SPACE);
							}
							value=value+sb.toString();
						}
					}
				}
			}
		}
		//如果为空则返回默认值
		return value==null?propertyMember.getDefaultValue():value.toString();
	}
	/**
	 * 对象查询注入
	 * @param rs
	 * 返回的对象
	 * @param propertyMember
	 * 属性成员
	 * @param entity
	 * 要注入值的对象
	 */
	public void resultSetInject(ResultSet rs,PropertyMember pm,Object entity) throws SQLException {
		Object value=null;
		try{
			if(DataTypeValidation.isBoolean.contains(pm.getReturnTypeSimpleName())){
				value=rs.getInt(pm.getFieldName())==Constants.TRUE?true:false;
			}else if(DataTypeValidation.isByte.contains(pm.getReturnTypeSimpleName())){
				value=rs.getByte(pm.getFieldName());
			}else if(DataTypeValidation.isChar.contains(pm.getReturnTypeSimpleName())){
				value=rs.getString(pm.getFieldName());
			}else if(DataTypeValidation.isShort.contains(pm.getReturnTypeSimpleName())){
				value=rs.getShort(pm.getFieldName());
			}else if(DataTypeValidation.isInteger.contains(pm.getReturnTypeSimpleName())){
				value=rs.getInt(pm.getFieldName());
			}else if(DataTypeValidation.isLong.contains(pm.getReturnTypeSimpleName())){
				value=rs.getLong(pm.getFieldName());
			}else if(DataTypeValidation.isFloat.contains(pm.getReturnTypeSimpleName())){
				value=rs.getFloat(pm.getFieldName());
			}else if(DataTypeValidation.isDouble.contains(pm.getReturnTypeSimpleName())){
				value=rs.getDouble(pm.getFieldName());
			}else if(DataTypeValidation.isBigDecimal.contains(pm.getReturnTypeSimpleName())){
				value=rs.getBigDecimal(pm.getFieldName());
			}else if(DataTypeValidation.isEnum.contains(pm.getReturnTypeSimpleName())){
				//把字符串转换为枚举类型
				value=rs.getString(pm.getFieldName());
				if(value!=null&&String.valueOf(value).length()>0){
					value = Enum.class.getDeclaredMethod(Constants.VALUEOF,new Class<?>[] { Class.class, String.class }).
							invoke(null, new Object[]{pm.getSMethod().getParameterTypes()[0],value}); 
				}
			}else if(DataTypeValidation.isString.contains(pm.getReturnTypeSimpleName())){
				if(pm.getCurrentFieldAnnotation()!=null&&pm.getCurrentFieldAnnotation().annotationType().equals(Lob.class)){
					BufferedReader br=null;
					try{
						Clob lob=rs.getClob(pm.getFieldName());
						if(lob!=null){
							br=new BufferedReader(lob.getCharacterStream());
							StringBuilder sbStr=new StringBuilder();
							String content=null;
							while((content=br.readLine())!=null){
								sbStr.append(content);
								content=null;
							}
							value=sbStr.toString();
							sbStr=null;
						}
					} finally{
						if(br!=null){
							try {
								br.close();
							}finally{
								br=null;
							}
						}
					}
				}else{
					value=rs.getString(pm.getFieldName());
				}
			}else if(DataTypeValidation.isDate.contains(pm.getReturnTypeSimpleName())){
				value=rs.getDate(pm.getFieldName());
			}else{
		 		throw new DataTypeException(Message.getMessage(Message.PM_5012,pm.getReturnTypeSimpleName()));
			}
			pm.getSMethod().invoke(entity, value);
		}  catch (Exception e) {
			Throwable cause=e.getCause();
			if(cause!=null){
				log.error(StackTraceInfo.getTraceInfo()+cause.getMessage());
		 		throw new HibernateException(cause);
			}else{
				log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
		 		throw new HibernateException(e);
			}
		}
	}
	/**
	 * @param method
	 * 注入数据的方法
	 * @param paramType
	 * 参数类型
	 * @param entity
	 * 实体对象
	 * @param value
	 * 值
	 */
	public void injectParameter(Object entity,Method method,String paramType, Object value){
		try{
			if(DataTypeValidation.isBoolean.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						Object val=null;
						try{
							//数字转换
							val=Integer.parseInt(value.toString())==Constants.TRUE?Boolean.TRUE:Boolean.FALSE;
						}catch(NumberFormatException nfe){
							//字符转换
							val=Boolean.parseBoolean(value.toString());
						}
						method.invoke(entity,val);
					}
				}
			}else if(DataTypeValidation.isByte.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity,Byte.parseByte(value.toString()));
					}
				}
			}else if(DataTypeValidation.isShort.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity, Short.parseShort(value.toString()));
					}
				}
			}else if(DataTypeValidation.isInteger.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity, Integer.parseInt(value.toString()));
					}
				}
			}else if(DataTypeValidation.isLong.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity, Long.parseLong(value.toString()));
					}
				}
			}else if(DataTypeValidation.isFloat.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity, Float.parseFloat(value.toString()));
					}
				}
			}else if(DataTypeValidation.isDouble.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity,Double.parseDouble(value.toString()));
					}
				}
			}else if(DataTypeValidation.isString.contains(paramType)){
				//字符串则不管是否为NULL或""都注入
				method.invoke(entity,value);
			}else if(DataTypeValidation.isDate.contains(paramType)){
				if(value!=null){
					if(!String.valueOf(value).isEmpty()){
						method.invoke(entity,value);
					}
				}
			}else{
		 		throw new HibernateException(Message.getMessage(Message.PM_5005,entity,method.getName(),value,paramType));
			}
		}catch(Exception e) {
			Throwable cause=e.getCause();
			String message=null;
			if(cause!=null){
				message=Message.getMessage(Message.PM_5004,
		 				entity,method.getName(),paramType,value)+cause.getMessage();
				log.error(StackTraceInfo.getTraceInfo()+message);
		 		throw new HibernateException(message);
			}else{
				message=Message.getMessage(Message.PM_5004,
		 				entity,method.getName(),paramType,value)+e.getMessage();
				log.error(StackTraceInfo.getTraceInfo()+message);
		 		throw new HibernateException(message);
			}
		}
	}
	/**
	 * 生成实体主键值
	 * 32位值：6fc089dfbbac4a76989e353d1397da3d
	 */
	public String generateKeyBy32(){
		Random ra=new Random();
		return DigestUtils.md5Hex(String.valueOf(UUID.randomUUID()) + System.currentTimeMillis() + ra.nextInt(999999999));
	}
}
