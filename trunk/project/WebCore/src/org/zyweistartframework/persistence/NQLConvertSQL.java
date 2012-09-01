package org.zyweistartframework.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.exception.HibernateException;
import org.zyweistartframework.utils.StackTraceInfo;
/**
 * NQL转换为原生SQL语句的类 
 * @author Start
 */
public final class NQLConvertSQL {
	
	private final static Log log=LogFactory.getLog(NQLConvertSQL.class);
	/**
	 * 获取语句中用单引号引起来的部分
	 * <pre>
	 * select o from sample o where o.name='value'获取的为'value'
	 * </pre>
	 */
	private final static String VALUECONTENTREGEX="'(.*?)'";
	/**
	 * 获取语句中的实体别名.字段
	 * <pre>
	 * select o from sample o where o.name='value'获取的为o.name
	 * </pre>
	 */
	private final static String FIELDREGEX="\\b[a-zA-Z_]\\w*\\.[a-zA-Z_]\\w*\\b";
	/**
	 * 查询字段为空时查询实体的表达式 
	 */
	private final static String FIELDEMPTYREGEX="\\b[a-zA-Z_]\\w*\\b";
	/**
	 * 别名获取实体的表达式
	 * <pre>
	 * select o from sample o where o.name='value'获取的为sample o
	 * </pre>
	 */
	private final static String ENTITYTABLEREGEX="\\b[a-zA-Z_]\\w*\\s+%s(\\s+|,)\\b";
	/**
	 * SQL语句的常用关键字
	 */
	private final static String SQLKEYWORD="|select|from|where|insert|into|values|update|set|delete|";
	
	private final static String REPLACESTR="!@#$%^&*()";
	
	private Map<String,String> valueContent=new HashMap<String,String>();
	/**
	 * 语句中查询的实体列表
	 */
	private Set<String> entitys=new HashSet<String>();
	/**
	 * 语句中查询实体别名.字段的列表
	 */
	private Map<String,Set<String>> fieldMaps=new HashMap<String,Set<String>>();
	
	private final String nativeNQL;
	/**
	 * 当前查询语句中对应实体的类列表
	 */
	private Set<Class<?>> prototypes=new HashSet<Class<?>>();
	/**
	 * 转换后的NSQL语句
	 */
	private String convertSql;
	
	public NQLConvertSQL(String nativeNQL){
		this.nativeNQL=nativeNQL;
		this.convertSql=nativeNQL;
	}
	
	public void analyze(){
		Matcher matcher=Pattern.compile(VALUECONTENTREGEX).matcher(convertSql);
		int i=0;
		while(matcher.find()){
			String gVal=matcher.group().trim();
			valueContent.put(REPLACESTR+(i++), gVal);
		}
		for(String e:valueContent.keySet()){
			convertSql=convertSql.replace(valueContent.get(e),e);
		}
		matcher=Pattern.compile(FIELDREGEX).matcher(convertSql);
		while(matcher.find()){
			String gVal=matcher.group().trim();
			String aliasName=gVal.split("\\.")[0];
			Set<String> fields=fieldMaps.get(aliasName);
			if(fields==null){
				fields=new HashSet<String>();
			}
			fields.add(gVal);
			fieldMaps.put(aliasName,fields);
		}
		//如果没有什么查询字段则直接解析类语句
		if(fieldMaps.isEmpty()){
			matcher=Pattern.compile(FIELDEMPTYREGEX).matcher(convertSql);
			List<String> sa=new ArrayList<String>();
			while(matcher.find()){
				String gVal=matcher.group().trim();
				if(SQLKEYWORD.indexOf("|"+gVal.toLowerCase()+"|")==-1){
					sa.add(gVal);
				}
			}
			for(String s:sa){
				i=0;
				for(String a:sa){
					if(a.equals(s)){
						i++;
					}
				}
				if(i==1){
					entitys.add(s+" "+sa.get(sa.size()-1));
				}
			}
		}else{
			for(String key:fieldMaps.keySet()){
				matcher=Pattern.compile(String.format(ENTITYTABLEREGEX,key)).matcher(convertSql);
				while(matcher.find()){
					String gVal=matcher.group().trim().replace(",", "");
					entitys.add(gVal);
				}
			}
		}
		Map<String,String> filterMap=new HashMap<String,String>();
		Map<String,String> tableEnttiyMap=new HashMap<String,String>();
		Map<String,String> fieldTableMap=new HashMap<String,String>();
		for(String en:entitys){
			String[] ens=en.split(" +");
			if(SQLKEYWORD.indexOf("|"+ens[0]+"|")==-1){
				EntityMember entityMember=EntityFactory.getInstance().getEntityMemberByEntityName(ens[0]);
				prototypes.add(entityMember.getPrototype());
				tableEnttiyMap.put(en,entityMember.getTableName()+" "+ens[1]);
				Set<String> fields=fieldMaps.get(ens[1]);
				filterMap.put(ens[1], ens[1]+".*");
				if(fields!=null){
					for(String field:fields){
						String[] fs=field.split("\\.");
						fieldTableMap.put(field,fs[0]+"."+PersistenceConfig.LEFTSEPARATED+getColumnFieldByEntityMember(entityMember,fs[1])+PersistenceConfig.RIGHTSEPARATED);
					}
				}
			}
		}
		for(String e:filterMap.keySet()){
			convertSql=convertSql.replaceAll("\\b"+e+"\\b", filterMap.get(e));
		}
		for(String e:tableEnttiyMap.keySet()){
			convertSql=convertSql.replace(""+e+".*", tableEnttiyMap.get(e));
		}
		for(String e:fieldTableMap.keySet()){
			String[] es=e.split("\\.");
			convertSql=convertSql.replace(""+es[0]+".*."+es[1], fieldTableMap.get(e));
		}
		for(String e:valueContent.keySet()){
			convertSql=convertSql.replace(e,valueContent.get(e));
		}
	}
	/**
	 * 原始传入的NQL查询语句
	 */
	public String getNativeNQL() {
		return nativeNQL;
	}
	
	public Set<Class<?>> getPrototypes() {
		return prototypes;
	}

	@Override
	public String toString() {
		return convertSql;
	}
	/**
	 * 根据实体的字段名称获取对应的数据库中的字段
	 */
	private String getColumnFieldByEntityMember(EntityMember entityMember,String fieldName){
		Set<PropertyMember> propertyMembers=new HashSet<PropertyMember>();
		//主键
		propertyMembers.add(entityMember.getPKPropertyMember());
		//成员
		propertyMembers.addAll(entityMember.getPropertyMembers());
		//主一对一
		propertyMembers.addAll(entityMember.getMainOneToOneProperyMembers());
		//一对一
		propertyMembers.addAll(entityMember.getOneToOneProperyMembers());
		//多对一
		propertyMembers.addAll(entityMember.getManyToOnePropertyMembers());
		//一对多
		propertyMembers.addAll(entityMember.getOneToManyPropertyMembers());
		//多对多
		propertyMembers.addAll(entityMember.getManyToManyPropertyMembers());
		//只要满足查询数据表字段为实体字段或数据表对应的字段即中
		for(PropertyMember propertyMember:propertyMembers){
			if(fieldName.equalsIgnoreCase(propertyMember.getField().getName())||
					fieldName.equalsIgnoreCase(propertyMember.getFieldName())){
				return propertyMember.getFieldName();
			}
		}
		String message=null;
		try{
			message=Message.getMessage(Message.PM_5011,entityMember.getEntityName(),fieldName);
			log.error(StackTraceInfo.getTraceInfo()+message);
			throw new HibernateException(message);
		}finally{
			message=null;
		}
	}
}
