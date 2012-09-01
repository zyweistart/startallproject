package org.zyweistartframework.persistence;

import java.lang.annotation.Annotation;

import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.Temporal;

public abstract class ContextAnnotation {
	
	public String convertSQLCreateQuery(PropertyMember propertyMember,String tableName){
		StringBuilder fieldBuilder=new StringBuilder();
		Annotation annotation=propertyMember.getCurrentFieldAnnotation();
		if(annotation!=null){
			if(Id.class==annotation.annotationType()){
				id(propertyMember,(Id)annotation,fieldBuilder);
			}else if(Column.class==annotation.annotationType()){
				column(propertyMember,(Column)annotation,fieldBuilder,tableName);
			}else if(Lob.class==annotation.annotationType()){
				lob(propertyMember,(Lob)annotation,fieldBuilder);
			}else if(Temporal.class==annotation.annotationType()){
				temporal(propertyMember,(Temporal)annotation,fieldBuilder);
			}
		}else{
			defaultDefinition(propertyMember,fieldBuilder);
		}
		return fieldBuilder.toString();
	}
	
	public abstract void id(PropertyMember propertyMember,Id id,StringBuilder fieldBuilder);
	
	public abstract void column(PropertyMember propertyMember,Column column,StringBuilder fieldBuilder,String tableName);
	
	public abstract void temporal(PropertyMember propertyMember,Temporal temporal,StringBuilder fieldBuilder);
	
	public abstract void lob(PropertyMember propertyMember,Lob lob,StringBuilder fieldBuilder);
	
	public abstract void defaultDefinition(PropertyMember propertyMember,StringBuilder fieldBuilder);
	
	public abstract void fieldDefinition(StringBuilder definitionSQL, String dataType, int length);
	
	public abstract String comment(String tableName,String fieldName,Annotation[] annotations);
	
}