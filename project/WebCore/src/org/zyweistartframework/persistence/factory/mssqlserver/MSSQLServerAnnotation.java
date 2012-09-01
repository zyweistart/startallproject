package org.zyweistartframework.persistence.factory.mssqlserver;

import java.lang.annotation.Annotation;

import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.exception.GenerateTableError;
import org.zyweistartframework.persistence.ContextAnnotation;
import org.zyweistartframework.persistence.PersistenceConfig;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.GeneratedValue;
import org.zyweistartframework.persistence.annotation.dt.TemporalType;


public final class MSSQLServerAnnotation extends ContextAnnotation {
	
	public void fieldDefinition(StringBuilder definitionSQL, String dataType, int length){
        if(DataTypeValidation.isShort.contains(dataType)){
        	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"SMALLINT"+PersistenceConfig.RIGHTSEPARATED);
        }else if (DataTypeValidation.isInteger.contains(dataType)){
            //32数字型
        	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"INT"+PersistenceConfig.RIGHTSEPARATED);
        }else if (DataTypeValidation.isLong.contains(dataType)){
            //64数字型
        	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"NUMERIC"+PersistenceConfig.RIGHTSEPARATED+"(19, 0)");
        }else if(DataTypeValidation.isFloat.contains(dataType)){
        	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"FLOAT"+PersistenceConfig.RIGHTSEPARATED);
        }else if (DataTypeValidation.isDouble.contains(dataType)){
            //带有小数点
        	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"DECIMAL"+PersistenceConfig.RIGHTSEPARATED+"(18, 2)");
        }else if(DataTypeValidation.isBoolean.contains(dataType)){
        	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"TINYINT"+PersistenceConfig.RIGHTSEPARATED);
        }else if(DataTypeValidation.isChar.contains(dataType)){
        	
        }else if (DataTypeValidation.isString.contains(dataType)){
            if (length == 0){
                //长度为零则表示为大文本型
            	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"TEXT"+PersistenceConfig.RIGHTSEPARATED);
            }else if(length<=100){
            	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"VARCHAR"+PersistenceConfig.RIGHTSEPARATED+"(" + length + ")");
            }else{//字符型
            	definitionSQL.append(PersistenceConfig.LEFTSEPARATED+"NVARCHAR"+PersistenceConfig.RIGHTSEPARATED+"(" + length + ")");
            }
        }else{
        	throw new GenerateTableError(Message.getMessage(Message.PM_5012,dataType));
        }
    }

	@Override
	public void id(PropertyMember propertyMember,Id id, StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
        fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(), id.length());
        if (id.value() == GeneratedValue.IDENTITY){
            //自动增长
            fieldBuilder.append(" IDENTITY(1,1)");
        }
        fieldBuilder.append(" NOT NULL,");
	}

	@Override
	public void column(PropertyMember propertyMember,Column column,StringBuilder fieldBuilder,String tableName) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
        if (!"".equals(column.columnDefinition())){
        	fieldBuilder.append(column.columnDefinition());
        }else{
            fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(),column.length());
        }
        if (!column.nullable()){
        	fieldBuilder.append(" NOT");
        }
        fieldBuilder.append(" NULL,");
        if (column.unique()){
        	fieldBuilder.append("CONSTRAINT REF_" + tableName + "_UNIQUE_" + propertyMember.getFieldName() +" UNIQUE(" + propertyMember.getFieldName() + "),");
        }
	}

	@Override
	public void temporal(PropertyMember propertyMember,Temporal temporal,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
    	if (temporal.value() == TemporalType.DATE){
            //默认格式：1989-06-24
            fieldDefinition(fieldBuilder, "String", 10);
        }else if (temporal.value() == TemporalType.TIME){
            //默认格式: 00:00:00
            fieldDefinition(fieldBuilder, "String",8);
        }else{
            //默认格式：1989-06-24 00:00:00
            fieldDefinition(fieldBuilder, "String", 19);
        }
        if (!temporal.nullable()){
        	fieldBuilder.append(" NOT");
        }
        fieldBuilder.append(" NULL,");
	}

	@Override
	public void lob(PropertyMember propertyMember,Lob lob,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
    	fieldDefinition(fieldBuilder, "String",0);
        if (!lob.nullable()){
        	fieldBuilder.append(" NOT");
        }
        fieldBuilder.append(" NULL,");
	}

	@Override
	public void defaultDefinition(PropertyMember propertyMember,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
        fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(), 255);
        fieldBuilder.append(" NULL,");
	}

	@Override
	public String comment(String tableName, String fieldName,
			Annotation[] annotations) {
		return null;
	}

}
