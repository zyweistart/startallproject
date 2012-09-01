package org.framework.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.entity.Root;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.persistence.annotation.Temporal;


/**
 * 自动生成NQL语句
 * @author Start
 */
public final class AutoBuilderSql {
	
	private AutoBuilderSql(){}
	
	public synchronized static Map<String,List<Object>> buildSql(Root entity) throws Exception{
		return buildSql(entity,new ArrayList<String>());
	}
	
	public synchronized static Map<String,List<Object>> buildSql(Root entity,List<String> special) throws Exception{
		EntityMember entityMember=EntityFactory.getInstance().getEntityMember(entity.getClass());
		List<Object> values=new ArrayList<Object>();
		StringBuilder builderSql=new StringBuilder();
		builderSql.append(Constants.SELECT);
		builderSql.append(Constants.SPACE);
		builderSql.append("*");
		builderSql.append(Constants.SPACE);
		builderSql.append(Constants.FROM);
		builderSql.append(Constants.SPACE);
		builderSql.append(entityMember.getTableName());
		builderSql.append(Constants.SPACE);
		builderSql.append(Constants.WHERE);
		builderSql.append(Constants.SPACE);
		builderSql.append("1=1");
		for(PropertyMember propertyMember :entityMember.getPropertyMembers()){
			//NoBuilderSQL标有该注解的实体字段表示不参加语句生成
			if(!propertyMember.getGMethod().isAnnotationPresent(NoBuilderSQL.class)){
				Object value=propertyMember.getGMethod().invoke(entity);
				if(value!=null&&!value.toString().isEmpty()){
					builderSql.append(Constants.SPACE);
					builderSql.append(Constants.AND);
					builderSql.append(Constants.SPACE);
					builderSql.append(propertyMember.getFieldName());
					if(propertyMember.getCurrentFieldAnnotation()!=null&&
							propertyMember.getCurrentFieldAnnotation().annotationType().equals(Temporal.class)){
						builderSql.append(Constants.EQ);
						builderSql.append(Constants.KEY);
						values.add(value);
					}else{
						if(DataTypeValidation.isNumber.contains(propertyMember.getReturnTypeSimpleName())){
							builderSql.append(Constants.EQ);
							builderSql.append(value);
						}else if(DataTypeValidation.isBoolean.contains(propertyMember.getReturnTypeSimpleName())){
							builderSql.append(Constants.EQ);
							if(Boolean.parseBoolean(value.toString())){
								builderSql.append(Constants.TRUE);
							}else{
								builderSql.append(Constants.FALSE);
							}
						}else{
							if(special.contains(propertyMember.getFieldName())){
								builderSql.append(Constants.EQ);
								builderSql.append(Constants.KEY);
								values.add(value);
							}else{
								builderSql.append(Constants.SPACE);
								builderSql.append(Constants.LIKE);
								builderSql.append(Constants.SPACE);
								builderSql.append(Constants.KEY);
								values.add("%");
								values.add(value);
								values.add("%");
							}
						}
					}
				}else{
					if(special.contains(propertyMember.getFieldName())){
						builderSql.append(Constants.SPACE);
						builderSql.append(Constants.AND);
						builderSql.append(Constants.SPACE);
						builderSql.append(propertyMember.getFieldName());
						builderSql.append(Constants.SPACE);
						builderSql.append(Constants.IS);
						builderSql.append(Constants.SPACE);
						builderSql.append(Constants.NULL);
					}
				}
			}
		}
		Map<String,List<Object>> map=new HashMap<String,List<Object>>();
		map.put(builderSql.toString(), values);
		return map;
	}
	
	public static String getNQL(Map<String, List<Object>> map) {
		if(map!=null){
			for(String key:map.keySet()){
				return key;
			}
		}
		return null;
	}

	public static Object[] getParams(Map<String, List<Object>> map) {
		if(map!=null){
			for(String key:map.keySet()){
				return (Object[]) map.get(key).toArray();
			}
		}
		return null;
	}
	/**
	 * 表示该字段不生成NQL语句
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NoBuilderSQL {}
	
}